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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
public class GoodOwner extends BaseRequest<Object> implements BaseObject {

    private Long accountId;
    private String fullName;
    private String nationality;
    private Long issueDate;
    private String issueBy;
    private Long gender;
    private Map<String, List<String>> attachProperties;
    private String phoneNumber;
    private String address;
    private String identityNo;
    private Long dateOfBirth;

    private static final Logger logger = Logger.getLogger(GoodOwner.class);

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(String issueBy) {
        this.issueBy = issueBy;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Map<String, List<String>> getAttachProperties() {
        return attachProperties;
    }

    public void setAttachProperties(Map<String, List<String>> attachProperties) {
        this.attachProperties = attachProperties;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.fullName = record.getString("FullName");
            this.nationality = record.getString("Nationality");
            this.gender = record.getLong("Gender");
            this.phoneNumber = record.getString("PhoneNumber");
            this.address = record.getString("Address");
            this.identityNo = record.getString("IdentityNo");
            this.issueDate = record.getLong("IssueDate");
            this.issueBy = record.getString("IssueBy");
            this.dateOfBirth = record.getLong("DateOfBirth");
            this.attachProperties = (Map<String, List<String>>) record.getMap("AttachProp");
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
            bins.add(new Bin("FullName", fullName));
            bins.add(new Bin("Nationality", nationality));
            bins.add(new Bin("IssueDate", issueDate));
            bins.add(new Bin("IssueBy", issueBy));
            bins.add(new Bin("Gender", gender));
            bins.add(new Bin("PhoneNumber", phoneNumber));
            bins.add(new Bin("Gender", gender));
            bins.add(new Bin("IdentityNo", identityNo));
            bins.add(new Bin("DateOfBirth", dateOfBirth));
            bins.add(new Bin("Address", (address)));
            bins.add(new Bin("AttachProp", attachProperties));
            return bins.toArray(new Bin[bins.size()]);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @JsonIgnore
    @Override
    public boolean parse(Map map) {
       try {
            this.accountId = (Long) map.get("AccountId");
            this.fullName = (String) map.get("FullName");
            this.nationality = (String) map.get("Nationality");
            this.gender = (Long) map.get("Gender");
            this.phoneNumber = (String) map.get("PhoneNumber");
            this.address = (String) map.get("Address");
            this.identityNo = (String) map.get("IdentityNo");
            this.issueDate = (Long) map.get("IssueDate");
            this.issueBy = (String) map.get("IssueBy");
            this.dateOfBirth = (Long) map.get("DateOfBirth");
            this.attachProperties = (Map<String, List<String>>)map.get("AttachProp");
            return true;
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
    }

}
