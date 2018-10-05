--
-- Created by IntelliJ IDEA.
-- User: HP
-- Date: 9/28/2017
-- Time: 10:48 PM
-- To change this template use File | Settings | File Templates.
--
function deleteRecord(rec)
    return aerospike:remove(rec)
end

function deleteRecordByFilter(rec, paramFilters)
    local check = true;
    for v in list.iterator(paramFilters) do
        if (v["operator"] == "=" and rec[v["field"]] ~= v["value"]) then
            check = false
            break
        elseif (v["operator"] == "<" and rec[v["field"]] >= v["value"]) then
            check = false
            break
        elseif (v["operator"] == ">" and rec[v["field"]] <= v["value"]) then
            check = false
            break
        elseif (v["operator"] == "<=" and rec[v["field"]] > v["value"]) then
            check = false
            break
        elseif (v["operator"] == ">=" and rec[v["field"]] < v["value"]) then
            check = false
            break
        end
    end
    if (check) then
        info("delete record " .. record.key(rec))
        return aerospike:remove(rec)
    else
        return 1;
    end;
end


function count_record(stream)
    local function mapper(rec)
        return 1
    end

    local function reducer(v1, v2)
        return v1 + v2;
    end

    return stream:map(mapper):reduce(reducer)
end

