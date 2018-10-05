package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.vtgo.vn.admin.base.BaseObject;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Map;

public class Account implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(Account.class);



    private long accountId;
    private String userName;
    private String password;
    private String phoneNumber;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean parse(Record record) {
        userName = record.getString("UserName");
        password = record.getString("Password");
        this.phoneNumber = record.getString("PhoneNumber");
        this.accountId = record.getLong("AccountId");
        return true;
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
