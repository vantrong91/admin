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
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class Balance implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(Balance.class);

    private long balType;
    private long gross;
    private long consume;
    private long reserve;
    private long expDate;
    private long balance;
    private String acctNumber;
//    private String code;

    public long getBalType() {
        return balType;
    }

    public void setBalType(long balType) {
        this.balType = balType;
    }

    public long getGross() {
        return gross;
    }

    public void setGross(long gross) {
        this.gross = gross;
    }

    public long getConsume() {
        return consume;
    }

    public void setConsume(long consume) {
        this.consume = consume;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public long getReserve() {
        return reserve;
    }

    public void setReserve(long reserve) {
        this.reserve = reserve;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
    public boolean parse(ResultSet rs) {
        //TODO Not using because data store in aerospike
        return false;
    }

    @Override
    public boolean parse(Record record) {
        return false;
    }

    /**
     * "Gross":3321, "BalType":1, "ExpDate":4126809600000, "Consume":1230
     *
     * @param object
     * @return
     */
    public boolean parseFromObject(Object object) {
        try {
            if (object == null) {
                return false;
            }
            if (object instanceof Map) {
                Map<String, Object> balanceInfo = (Map<String, Object>) object;
                this.balType = (Long) balanceInfo.get("BalType");
                this.gross = (Long) balanceInfo.get("Gross");
                this.expDate = (Long) balanceInfo.get("ExpDate");
                this.consume = (Long) balanceInfo.get("Consume");
                this.reserve = (Long) balanceInfo.get("Reserve");
                this.balance = this.gross - this.consume - this.reserve;
                this.acctNumber = (String) balanceInfo.get("AcctNumber");
//                this.code = (String) balanceInfo.get("Code");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Balance{" + "balType=" + balType + ", gross=" + gross + ", consume=" + consume + ", reserve=" + reserve + ", expDate=" + expDate + '}';
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