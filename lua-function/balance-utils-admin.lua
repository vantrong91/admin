
--module BalanceUtils
function createBalance(rec,balType,gross,consume,expDate,reserve,acctNumber,account)
	local ret = map()
	-- if not aerospike:exists(rec) then 
 --       	ret["ResultText"]="Record customer not existed"
	-- 	ret["ResultCode"]="1"
	-- 	return ret
 --    end
	local mapBalance = rec["Balance"]
	if (mapBalance == nil) then
		mapBalance = map()
	end
	local balanceInfo = mapBalance[balType]
	if (balanceInfo ~= nill) then
		ret["ResultText"]="Balance type " .. balType .. " existed"
		ret["ResultCode"]="1"
		return ret
	end
	balanceInfo = map()
	balanceInfo["Gross"] = gross
	balanceInfo["Consume"] = consume
	balanceInfo["ExpDate"] = expDate
	balanceInfo["BalType"] = balType
	balanceInfo["Reserve"] = reserve
	balanceInfo["AcctNumber"] = acctNumber
	mapBalance[balType] = balanceInfo
	rec["Balance"] = mapBalance
	rec["AccountId"] = account
	local update_result = 0
	if not aerospike:exists(rec) then
		update_result = aerospike:create(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	else 
		update_result = aerospike:update(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	end
	ret["ResultCode"]=""..update_result
	return ret
end


function updateBalance(rec,balType,change)
	local ret = map()
	-- if not aerospike:exists(rec) then 
 --       	ret["ResultText"]="Record customer not existed"
	-- 	ret["ResultCode"]="1"
	-- 	return ret
 --    end
	local mapBalance = rec["Balance"]
	if (mapBalance == nil) then
		mapBalance = map()
	end
	local balanceInfo = mapBalance[balType]
	if (balanceInfo == nill) then
		if (change < 0) then
			ret["ResultText"]="Balance "..balType.." not existed to charge"
			ret["ResultCode"]="1"
			return ret
		end
		balanceInfo = map()
		balanceInfo["Gross"] = change
		balanceInfo["Consume"] = 0
		balanceInfo["ExpDate"] = 4126809600000
		balanceInfo["BalType"] = balType
	else
		if (change > 0) then
			balanceInfo["Gross"] = balanceInfo["Gross"] + change
		else
			balanceInfo["Consume"] = balanceInfo["Consume"] - change
		end
	end
	if (balanceInfo["Gross"] < (balanceInfo["Consume"] + balanceInfo["Reserve"])) then
		ret["ResultText"]="Balance not enough"
		ret["ResultCode"]="4"
		return ret
	end
	mapBalance[balType] = balanceInfo
	rec["Balance"] = mapBalance
	local update_result = 0
	if not aerospike:exists(rec) then
		update_result = aerospike:create(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	else 
		update_result = aerospike:update(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	end

	-- local update_result = aerospike:update(rec)
	-- if (update_result == 0) then
	-- 	ret["ResultText"]="Success"
	-- end
	ret["ResultCode"]=""..update_result
	return ret
end

function getCode(rec,balType,code,withdrawAmount)
	local ret = map()
	-- if not aerospike:exists(rec) then 
 --       	ret["ResultText"]="Record customer not existed"
	-- 	ret["ResultCode"]="1"
	-- 	return ret
 --    end
	local mapBalance = rec["Balance"]
	local balanceInfo = mapBalance[balType]
	balanceInfo["Reserve"] = withdrawAmount
	mapBalance[balType] = balanceInfo
	rec["Balance"] = mapBalance
	rec["Code"] = code
	local update_result = 0
	if not aerospike:exists(rec) then
		update_result = aerospike:create(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
			ret["Code"]=""..code
		end
	else 
		update_result = aerospike:update(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
			ret["Code"]=""..code
		end
	end

	-- local update_result = aerospike:update(rec)
	-- if (update_result == 0) then
	-- 	ret["ResultText"]="Success"
	-- end
	ret["ResultCode"]=""..update_result
	return ret
end

function withdrawAmount(rec,balType)
	local ret = map()
	-- if not aerospike:exists(rec) then 
 --       	ret["ResultText"]="Record customer not existed"
	-- 	ret["ResultCode"]="1"
	-- 	return ret
 --    end
	local mapBalance = rec["Balance"]
	if (mapBalance == nil) then
		mapBalance = map()
	end
	local balanceInfo = mapBalance[balType]
	if (balanceInfo == nil) then
		ret["ResultText"]="Balance "..balType.." not existed to withdraw"
		ret["ResultCode"]="1"
		return ret
	else
		balanceInfo["Consume"] = balanceInfo["Consume"] + balanceInfo["Reserve"]
		balanceInfo["Reserve"] = 0
	end
	mapBalance[balType] = balanceInfo
	rec["Balance"] = mapBalance
	rec["Code"] = nil
	local update_result = 0
	if not aerospike:exists(rec) then
		update_result = aerospike:create(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	else 
		update_result = aerospike:update(rec)
		if (update_result == 0) then
			ret["ResultText"]="Success"
		end
	end

	-- local update_result = aerospike:update(rec)
	-- if (update_result == 0) then
	-- 	ret["ResultText"]="Success"
	-- end
	ret["ResultCode"]=""..update_result
	return ret
end


function rollbackUpdateBalance(rec,balType,change)
	local ret = map()
	if not aerospike:exists(rec) then 
       	ret["ResultText"]="Record customer not existed"
		ret["ResultCode"]="1"
		return ret
    end
	local mapBalance = rec["Balance"]
	if (mapBalance == nil) then
		mapBalance = map()
	end
	local balanceInfo = mapBalance[balType]
	if (balanceInfo == nill) then
		ret["ResultText"]="Balance "..balType.." not existed to charge"
		ret["ResultCode"]="1"
		return ret
	else
		if (change > 0) then
			balanceInfo["Gross"] = balanceInfo["Gross"] - change
		else
			balanceInfo["Consume"] = balanceInfo["Consume"] + change
		end
	end
	
	mapBalance[balType] = balanceInfo
	rec["Balance"] = mapBalance
	local update_result = aerospike:update(rec)
	if (update_result == 0) then
		ret["ResultText"]="Success"
	end
	ret["ResultCode"]=""..update_result
	return ret
end
