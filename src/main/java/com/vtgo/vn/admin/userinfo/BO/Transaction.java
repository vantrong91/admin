/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.vtgo.vn.admin.base.BaseObject;
import java.io.Serializable;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class Transaction implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(Balance.class);
    private long accountId;
    private long balType;
    private long change;
    private String content;
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getBalType() {
        return balType;
    }

    public void setBalType(long balType) {
        this.balType = balType;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Transaction{" + "accountId=" + accountId + ", balType=" + balType + ", change=" + change + '}';
    }

    @Override
    public boolean parse(Record record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
