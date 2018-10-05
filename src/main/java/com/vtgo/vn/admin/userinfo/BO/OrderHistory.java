/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.base.BaseObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class OrderHistory implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(OrderHistory.class);

    private long updateTime;
    private String content;
    private String type;

    public Map toMap() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("UpdateTime", updateTime);
        ret.put("Content", content);
        ret.put("Type", type);
        return ret;
    }

    public boolean parseFromMap(Map data) {
        try {
            this.updateTime = (Long) data.get("UpdateTime");
            this.content = (String) data.get("Content");
            this.type = (String) data.get("Type");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean parse(ResultSet rs) {
        //TODO Not using because data store in aerospike
        return false;
    }

    @Override
    public boolean parse(Record record) {
        return false;
    }

    @Override
    public boolean parse(Map<String, Object> map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bin[] toBins() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
