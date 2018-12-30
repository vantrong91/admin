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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class Bank implements BaseObject, Serializable {

    public static final Logger log = Logger.getLogger(Bank.class.getName());
    private String bankCode;
    private long accountId;
    private String bankName;
    private String bankNumber;
    private String fullName;
    private String bankBranch;

    public String getBankCode() {
        return bankCode;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    @Override
    public boolean parse(Record record) {
        try {
            bankCode = (String) record.getString("BankCode");
            accountId = (Long) record.getLong("AccountId");
            bankName = (String) record.getString("BankName");
            bankNumber = (String) record.getString("BankNumber");
            fullName = (String) record.getString("FullName");
            bankBranch = (String) record.getString("BankBranch");
            return true;
        } catch (Exception ex) {
            log.error(ex);
            return false;
        }
    }

    @Override
    public boolean parse(Map<String, Object> map) {
        try {
            bankCode = (String) map.get("BankCode");
            accountId = (Long) map.get("AccountId");
            bankName = (String) map.get("BankName");
            bankNumber = (String) map.get("BankNumber");
            fullName = (String) map.get("FullName");
            bankBranch = (String) map.get("BankBranch");
            return true;
        } catch (Exception ex) {
            log.error(ex);
            return false;
        }
    }

    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bank = new ArrayList<>();
            bank.add(new Bin("BankCode", bankCode));
            bank.add(new Bin("AccountId", accountId));
            bank.add(new Bin("BankName", bankName));
            bank.add(new Bin("BankNumber", bankNumber));
            bank.add(new Bin("FullName", fullName));
            bank.add(new Bin("BankBranch", bankBranch));
            return bank.toArray(new Bin[bank.size()]);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

}
