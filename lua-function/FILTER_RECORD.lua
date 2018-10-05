function FILTER_RECORD(stream, arguments)
    local paramSorts;
    local paramFilters;

    if arguments ~= nil then
        paramSorts = arguments["sorters"]
        paramFilters = arguments["filters"]
    end

    local function map_record(rec, fields)
        local result = map()

        if fields ~= nil then
            -- selected fields
            for v in list.iterator(fields) do
                result[v] = rec[v]
            end
        end

        if fields == nil then
            -- all fields
            local names = record.bin_names(rec)
            for i, v in ipairs(names) do
                result[v] = rec[v]
            end
        end
        result["PK"] = record.key(rec)
        return result
    end

    local function compare(x, y, order, type)
        if order == nil then
            order = "ASC"
        end
        if type == nil then
            type = "STRING"
        end
        --compare as string value
        if type == "STRING" then
            if order == "ASC" then
                return string.lower(tostring(y)) > string.lower(tostring(x))
            else -- DESC
                return string.lower(tostring(x)) > string.lower(tostring(y))
            end
            -- compare as number
        else
            if order == "ASC" then
                return (tonumber(y) or 0) > (tonumber(x) or 0)
            else -- DESC
                return (tonumber(x) or 0) > (tonumber(y) or 0)
            end
        end
    end

    -- insert a rec into a sorted list, return the insertion index for merge sort
    local function insert_sort(sorted_list, rec_map, start_index)
        len = list.size(sorted_list)

        if paramSorts ~= nil then
            for i = start_index or 1, len do
                for v in list.iterator(paramSorts) do

                    v1 = rec_map[v["sort_key"]]

                    v2 = sorted_list[i][v["sort_key"]]

                    if (v1 == nil and v2 ~= nil) then
                        list.append(sorted_list, rec_map)
                        return len
                    end

                    if (v1 ~= nil and v2 == nil) then
                        list.insert(sorted_list, i, rec_map)
                        return i
                    end

                    if (v1 ~= nil and v2 ~= nil) then
                        if (compare(v1, v2, v["order"], v["type"])) then
                            list.insert(sorted_list, i, rec_map)
                            return i
                        elseif (compare(v1, v2, v["order"], v["type"])) then
                            break
                        end
                    end
                end
            end
        end

        list.append(sorted_list, rec_map)

        return len
    end

    local function sort_aggregator()
        -- insert a rec into a sorted list is quite easy
        return function(sorted_list, rec)
            -- convert rec to map
            local rec_map = map_record(rec)

            -- apply orderBy
            insert_sort(sorted_list, rec_map)
            return sorted_list
        end
    end

    local function sort_reducer()
        return function(sorted_list1, sorted_list2)
            local start_index;
            for i = 1, list.size(sorted_list2) do
                local rec_map = sorted_list2[i]
                start_index = insert_sort(sorted_list1, rec_map, start_index)
            end
            return sorted_list1
        end
    end

 local function filter_record(record)
    if paramFilters ~= nil then
        --[[ info(paramFilters)
        if (record["OwnerType"] == tonumber(paramFilters["OwnerType"])) and (string.find(record["FullName"], paramFilters["FullName"]) or string.find(record["ContactPhone"], paramFilters["ContactPhone"])) then
            return true;
        else
            return false;
        end
         end ]]

        local check1 = true;
        local check2 = false;
        local flag= true;
        for v in list.iterator(paramFilters) do
            info(v["field"] .. v["operator"] .. v["value"])
            if (v["operator"] == "=") then
                info(v["operator"])
                if (record[v["field"]] == v["value"]) then
                    check1 = true
                    info("check1=true")
                else 
                    check1 = false
                    info("check1=false")
                end
            else 
                info(v["operator"])
                if (v["operator"] == "contain") then
                     flag=false;
                     if(record[v["field"]]~=nil and string.find(record[v["field"]], v["value"])) then                    
                        check2 = true
                        info("check2=true")
                      end
                end
            end
        end
        info(check1 == true and 1 or check1 == false and 0)
        info(check2 == true and 1 or check2 == false and 0)
        if ((check1 and flag) or ( check1 and check2 and flag==false)) then
            info("return=true")
            return true
        else
            return false;
        end
    end
end

    local aggregator = sort_aggregator()
    local reducer = sort_reducer()

    return stream:filter(filter_record):aggregate(list(), aggregator):reduce(reducer)
end