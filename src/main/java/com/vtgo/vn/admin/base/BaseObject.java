package com.vtgo.vn.admin.base;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import java.sql.ResultSet;
import java.util.Map;

public interface BaseObject{

   // public boolean parse(ResultSet rs);

    public boolean parse(Record record);
    
    public boolean parse(Map<String,Object> map);

    public Bin[] toBins();

}
