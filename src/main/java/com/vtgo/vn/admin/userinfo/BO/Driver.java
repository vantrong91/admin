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
 * @author HP
 */
public class Driver extends BaseRequest<Object> implements BaseObject {

    private static final Logger logger = Logger.getLogger(Driver.class);
    private Long accountId;
    private String fullName;
    private String nationality;
    private String licenseNo;
    private Long issueDate;
    private String issueBy;
    private Long gender;
    private String ethnic;
    private String email;
    private String phoneNumber;
    private String extLicenseNo;
    private Long extIssueDate;
    private String extIssueBy;
    private Long vehicleId;
    private Map<String, String> contactAddress;
    private Map<String, String> address;
    private Map<Integer, Map<String, String>> attachProperties;
    private Long state;
    private String driverCode;

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
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

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
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
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
//
//    public String getWards() {
//        return wards;
//    }
//
//    public void setWards(String wards) {
//        this.wards = wards;
//    }
//
//    public String getHouseholdNo() {
//        return householdNo;
//    }
//
//    public void setHouseholdNo(String householdNo) {
//        this.householdNo = householdNo;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }

    public String getExtLicenseNo() {
        return extLicenseNo;
    }

    public void setExtLicenseNo(String extLicenseNo) {
        this.extLicenseNo = extLicenseNo;
    }

    public Long getExtIssueDate() {
        return extIssueDate;
    }

    public void setExtIssueDate(Long extIssueDate) {
        this.extIssueDate = extIssueDate;
    }

    public String getExtIssueBy() {
        return extIssueBy;
    }

    public void setExtIssueBy(String extIssueBy) {
        this.extIssueBy = extIssueBy;
    }

    public Map<String, String> getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(Map<String, String> contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public Map<Integer, Map<String, String>> getAttachProperties() {
        return attachProperties;
    }

    public void setAttachProperties(Map<Integer, Map<String, String>> attachProperties) {
        this.attachProperties = attachProperties;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.fullName = record.getString("FullName");
            this.email = record.getString("Email");
            this.nationality = record.getString("Nationality");
            this.licenseNo = record.getString("LicenseNo");
            this.issueDate = record.getLong("IssueDate");
            this.issueBy = record.getString("IssueBy");
            this.gender = record.getLong("Gender");
            this.ethnic = record.getString("Ethnic");
            this.phoneNumber = record.getString("PhoneNumber");
            this.extLicenseNo = record.getString("ExtLicenseNo");
            this.extIssueDate = record.getLong("ExtIssueDate");
            this.extIssueBy = record.getString("ExtIssueBy");
            this.vehicleId = record.getLong("VehicleId");
            this.state = record.getLong("State");
            this.contactAddress = (Map<String, String>) record.getMap("ContactAddress");
            this.address = (Map<String, String>) record.getMap("Address");
            this.attachProperties = (Map<Integer, Map<String, String>>) record.getMap("AttachProp");
            this.driverCode = record.getString("DriverCode");

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
            bins.add(new Bin("Email", email));
            bins.add(new Bin("Nationality", nationality));
            bins.add(new Bin("LicenseNo", licenseNo));
            bins.add(new Bin("IssueDate", issueDate));
            bins.add(new Bin("IssueBy", issueBy));
            bins.add(new Bin("Gender", gender));
            bins.add(new Bin("Ethnic", ethnic));
            bins.add(new Bin("PhoneNumber", phoneNumber));
            bins.add(new Bin("ExtLicenseNo", extLicenseNo));
            bins.add(new Bin("ExtIssueDate", extIssueDate));
            bins.add(new Bin("ExtIssueBy", extIssueBy));
            bins.add(new Bin("VehicleId", vehicleId));
            bins.add(new Bin("State", state));
            bins.add(new Bin("ContactAddress", (contactAddress)));
            bins.add(new Bin("Address", (address)));
            bins.add(new Bin("AttachProp", attachProperties));
            bins.add(new Bin("DriverCode", "US" + phoneNumber));
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
            this.fullName = (String) map.get("FullName");
            this.email = (String) map.get("Email");
            this.nationality = (String) map.get("Nationality");
            this.licenseNo = (String) map.get("LicenseNo");
            this.issueDate = (Long) map.get("IssueDate");
            this.issueBy = (String) map.get("IssueBy");
            this.gender = (Long) map.get("Gender");
            this.ethnic = (String) map.get("Ethnic");
            this.phoneNumber = (String) map.get("PhoneNumber");
            this.extLicenseNo = (String) map.get("ExtLicenseNo");
            this.extIssueDate = (Long) map.get("ExtIssueDate");
            this.extIssueBy = (String) map.get("ExtIssueBy");
            this.vehicleId = (Long) map.get("VehicleId");
            this.state = (Long) map.get("State");
            this.contactAddress = (Map<String, String>) map.get("ContactAddress");
            this.address = (Map<String, String>) map.get("Address");
            this.attachProperties = (Map<Integer, Map<String, String>>) map.get("AttachProp");
            this.driverCode = (String) map.get("DriverCode");

            return true;
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
    }

}
