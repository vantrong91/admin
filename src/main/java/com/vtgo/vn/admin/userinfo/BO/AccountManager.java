/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.vtgo.vn.admin.base.BaseObject;
import com.vtgo.vn.admin.base.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class AccountManager extends BaseRequest<Object> implements BaseObject {

    private static final Logger log = Logger.getLogger(AccountManager.class.getName());
    private long accountId;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Long accountType;
    private String accountToken;
    private String osType;
    private String deviceToken;
    private String salt;
    private String accountCode;
    private String fileAvata;
    private Long state;
    private String bankCodeTran;

    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.password = record.getString("Password");
            this.email = record.getString("Email");
            this.phoneNumber = record.getString("PhoneNumber");
            this.accountType = record.getLong("AccountType");
            this.accountToken = record.getString("AccountToken");
            this.osType = record.getString("OsType");
            this.deviceToken = record.getString("DeviceToken");
            this.salt = record.getString("Salt");
            this.fullName = record.getString("FullName");
            this.accountCode = record.getString("AccountCode");
            this.fileAvata = record.getString("FileAvata");
            this.state = record.getLong("State");
            this.bankCodeTran = record.getString("BankCodeTran");

            return true;
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean parse(Map map) {
        try {
            this.accountId = (Long) map.get("AccountId");
            this.password = (String) map.get("Password");
            this.email = (String) map.get("Email");
            this.phoneNumber = (String) map.get("PhoneNumber");
            this.accountType = (Long) map.get("AccountType");
            this.accountToken = (String) map.get("AccountToken");
            this.osType = String.valueOf(map.get("OsType"));
            this.deviceToken = (String) map.get("DeviceToken");
            this.salt = (String) map.get("Salt");
            this.fullName = (String) map.get("FullName");
            this.accountCode = (String) map.get("AccountCode");
            this.fileAvata = (String) map.get("FileAvata");
            this.state = (Long) map.get("State");
            this.bankCodeTran = (String) map.get("BankCodeTran");
            return true;
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public Bin[] toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("AccountId", accountId));
        bins.add(new Bin("Password", password));
        bins.add(new Bin("Email", email));
        bins.add(new Bin("PhoneNumber", phoneNumber));
        bins.add(new Bin("AccountType", accountType));
        bins.add(new Bin("AccountToken", accountToken));
        bins.add(new Bin("OsType", osType));
        bins.add(new Bin("DeviceToken", deviceToken));
        bins.add(new Bin("Salt", salt));
        bins.add(new Bin("FullName", fullName));
        bins.add(new Bin("AccountCode", accountCode));
        bins.add(new Bin("FileAvata", fileAvata));
        bins.add(new Bin("State", state));
        bins.add(new Bin("BankCodeTran", bankCodeTran));
        return bins.toArray(new Bin[bins.size()]);
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getFileAvata() {
        return fileAvata;
    }

    public void setFileAvata(String fileAvata) {
        this.fileAvata = fileAvata;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getBankCodeTran() {
        return bankCodeTran;
    }

    public void setBankCodeTran(String bankCode) {
        this.bankCodeTran = bankCode;
    }

    @Override
    public String toString() {
        return "AccountManager{" + "accountId=" + accountId + ", password=" + password + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", accountType=" + accountType + ", accountToken=" + accountToken + ", osType=" + osType + ", deviceToken=" + deviceToken + ", salt=" + salt + ", accountCode=" + accountCode + ", fileAvata=" + fileAvata + ", state=" + state + ", bankCodeTran=" + bankCodeTran + '}';
    }
}
