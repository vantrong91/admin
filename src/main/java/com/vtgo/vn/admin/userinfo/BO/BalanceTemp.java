/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtgo.vn.admin.base.BaseObject;
import com.vtgo.vn.admin.base.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
/**
 *
 * @author Admin
 */
public class BalanceTemp extends BaseRequest<Object> implements BaseObject{
    private static final Logger logger = Logger.getLogger(Driver.class);
    private Long accountId;
    private Map<Long,Map<String,Object>> balance;
    private Long debt;
    private String code;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Map<Long, Map<String, Object>> getBalance() {
        return balance;
    }

    public void setBalance(Map<Long, Map<String, Object>> balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }
    
    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.balance = (Map<Long,Map<String,Object>>) record.getMap("Balance");
            this.debt = record.getLong("Debt");
            this.code = record.getString("Code");
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
        return true;
    }

    @JsonIgnore
    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bins = new ArrayList<>();
            bins.add(new Bin("AccountId", accountId));
            bins.add(new Bin("Balance", balance));
            bins.add(new Bin("Debt", debt));
            bins.add(new Bin("Code", code));
            
            return bins.toArray(new Bin[bins.size()]);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean parse(Map map) {
        try {
            this.accountId = (Long) map.get("AccountId");
            this.balance = (Map<Long,Map<String,Object>>) map.get("Balance");
            this.debt = (Long) map.get("Debt");
            this.code = (String) map.get("Code");
            return true;
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
    }

    @Override
    public String toString() {
        return "BalanceTemp{" + "accountId=" + accountId + ", balance=" + balance + ", debt=" + debt + ", code=" + code + '}';
    }
    
}
