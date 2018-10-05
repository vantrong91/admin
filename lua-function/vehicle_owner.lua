local function rec_to_map(rec)
   local xrec = map()
   for i, bin_name in ipairs(record.bin_names(rec)) do
       xrec[bin_name] = rec[bin_name]
   end
   return xrec
end

function search(stream, name,filterValueInMap,filterValueInList)
   local function name_filter(rec)
      local owner_name = rec['name']
      if(string.find(owner_name, name) ~= nil) then
         return true
      else
         return false
      end
   end
   local function map_filter(rec)
      local mapValue = rec['mapValue']
      if(mapValue == nil) then
         info("mapValue nil")
         return false
      end
      info("type map:%s",type(mapValue))
      for key, value in map.pairs(mapValue) do
         if(string.find(key, filterValueInMap) ~= nil or string.find(value, filterValueInMap) ~= nil) then
            return true
         end
      end
      return false
   end
   local function list_filter(rec)
      local listValue = rec['listValue']
      if(listValue == nil) then
         info("listValue nil")
         return false
      end
      for value in list.iterator(listValue) do
         info("%s", tostring(value))
         if(string.find(value, filterValueInList) ~= nil) then
            return true
         end
      end
      return false
   end
   return stream:filter(name_filter):filter(map_filter):filter(list_filter):map(rec_to_map)
end