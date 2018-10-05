package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtgo.vn.admin.base.BaseObject;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class BankAccount implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(BankAccount.class);
    private Long accountId;
    private String bankCode;
    private String accountNumber;
    private String branch;
    private String ownerName;

    public BankAccount() {
    }

    public BankAccount(String bankCode, String accountNumber, String branch) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.branch = branch;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    

    @Override
    @JsonIgnore
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
        return true;
    }

    public Map parseToMap() {
        Map ret = new HashMap();
        ret.put("BankCode", this.bankCode);
        ret.put("AccountNumber", this.accountNumber);
        ret.put("Branch", this.branch);
        return ret;
    }

    @Override
    @JsonIgnore
    public boolean parse(Map<String, Object> map) {
        try {
            this.accountId = (Long) map.get("AccountId");
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
        return true;
    }

    @Override
    @JsonIgnore
    public Bin[] toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("AccountId", accountId));
        return bins.toArray(new Bin[bins.size()]);
    }
}
