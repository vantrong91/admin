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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
public class VehicleOwner extends BaseRequest<Object> implements BaseObject {

    private static final Logger logger = Logger.getLogger(VehicleOwner.class);

    private Long accountId;
    private String fullName;
    private String director;
    private String taxCode;
    private String contactPhone;
    private String email;
    private String fax;
    private String website;
    private String contactPerson;
    private String contactPersonPhone;
    private String contactPersonEmail;
    private String businessLicense;
    private Long businessLicenseIssueDate;
    private String businessLicenseIssueBy;
    private String moderator;
    private String moderatorLicense;
    private Long moderatorLicenseIssueDate;
    private Long moderatorLicenseExpDate;
    private String companyPhone;
    private String businessTransportLicense;
    private Long businessTransportLicenseIssueDate;
    private Long businessTransportLicenseExpDate;
    private String nationality;
    private String licenseNo;
    private Long issueDate;
    private String issueBy;
    private Long gender;
    private String ethnic;
    private Long vehicleOwnerType;
    private Map<String, String> contactAddress;
    private Map<String, String> address;
    private Map<String, List<String>> attachProperties;

    private List<BankAccount> bankAccountLst;

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public Long getBusinessLicenseIssueDate() {
        return businessLicenseIssueDate;
    }

    public void setBusinessLicenseIssueDate(Long businessLicenseIssueDate) {
        this.businessLicenseIssueDate = businessLicenseIssueDate;
    }

    public String getBusinessLicenseIssueBy() {
        return businessLicenseIssueBy;
    }

    public void setBusinessLicenseIssueBy(String businessLicenseIssueBy) {
        this.businessLicenseIssueBy = businessLicenseIssueBy;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getModeratorLicense() {
        return moderatorLicense;
    }

    public void setModeratorLicense(String moderatorLicense) {
        this.moderatorLicense = moderatorLicense;
    }

    public Long getModeratorLicenseIssueDate() {
        return moderatorLicenseIssueDate;
    }

    public void setModeratorLicenseIssueDate(Long moderatorLicenseIssueDate) {
        this.moderatorLicenseIssueDate = moderatorLicenseIssueDate;
    }

    public Long getModeratorLicenseExpDate() {
        return moderatorLicenseExpDate;
    }

    public void setModeratorLicenseExpDate(Long moderatorLicenseExpDate) {
        this.moderatorLicenseExpDate = moderatorLicenseExpDate;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getBusinessTransportLicense() {
        return businessTransportLicense;
    }

    public void setBusinessTransportLicense(String businessTransportLicense) {
        this.businessTransportLicense = businessTransportLicense;
    }

    public Long getBusinessTransportLicenseIssueDate() {
        return businessTransportLicenseIssueDate;
    }

    public void setBusinessTransportLicenseIssueDate(Long businessTransportLicenseIssueDate) {
        this.businessTransportLicenseIssueDate = businessTransportLicenseIssueDate;
    }

    public Long getBusinessTransportLicenseExpDate() {
        return businessTransportLicenseExpDate;
    }

    public void setBusinessTransportLicenseExpDate(Long businessTransportLicenseExpDate) {
        this.businessTransportLicenseExpDate = businessTransportLicenseExpDate;
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

    public Long getVehicleOwnerType() {
        return vehicleOwnerType;
    }

    public void setVehicleOwnerType(Long vehicleOwnerType) {
        this.vehicleOwnerType = vehicleOwnerType;
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

    public Map<String, List<String>> getAttachProperties() {
        return attachProperties;
    }

    public void setAttachProperties(Map<String, List<String>> attachProperties) {
        this.attachProperties = attachProperties;
    }

    public List<BankAccount> getBankAccountLst() {
        return bankAccountLst;
    }

    public void setBankAccountLst(List<BankAccount> bankAccountLst) {
        this.bankAccountLst = bankAccountLst;
    }

    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.fullName = record.getString("FullName");
            this.contactPhone = record.getString("ContactPhone");
            this.contactPersonPhone = record.getString("ContactPnPhone");
            this.contactPerson = record.getString("ContactPn");
            this.contactPersonEmail = record.getString("ContactPnEmail");
            this.businessLicense = record.getString("BusiLic");
            this.businessLicenseIssueDate = record.getLong("BusiLicIssDate");
            this.businessLicenseIssueBy = record.getString("BusiLicIssBy");
            this.moderator = record.getString("Mod");
            this.moderatorLicense = record.getString("ModLicense");
            this.moderatorLicenseIssueDate = record.getLong("ModLicIssDate");
            this.moderatorLicenseExpDate = record.getLong("ModLicExpDate");
            this.email = record.getString("Email");
            this.director = record.getString("Director");
            this.taxCode = record.getString("TaxCode");
            this.fax = record.getString("Fax");
            this.website = record.getString("Website");
            this.companyPhone = record.getString("CompanyPhone");
            this.businessTransportLicense = record.getString("TranLic");
            this.businessTransportLicenseIssueDate = record.getLong("TranLicIssDate");
            this.businessTransportLicenseExpDate = record.getLong("TranLicExpDate");
            this.nationality = record.getString("Nationality");
            this.licenseNo = record.getString("LicenseNo");
            this.issueDate = record.getLong("IssueDate");
            this.issueBy = record.getString("IssueBy");
            this.gender = record.getLong("Gender");
            this.vehicleOwnerType = record.getLong("OwnerType");
            this.ethnic = record.getString("Ethnic");
            Map<String, String> contactAddressMap = (Map<String, String>) record.getMap("ContactAddress");
            this.contactAddress = contactAddressMap == null ? new HashMap<>() : contactAddressMap;
            Map<String, String> addressMap = (Map<String, String>) record.getMap("Address");
            this.address = addressMap == null ? new HashMap<>() : addressMap;
            Map<String, List<String>> attachPropertiesMap = (Map<String, List<String>>) record.getMap("AttachProp");
            this.attachProperties = attachPropertiesMap == null ? new HashMap<>() : attachPropertiesMap;
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
            bins.add(new Bin("ContactPhone", contactPhone));
            bins.add(new Bin("Email", email));
            bins.add(new Bin("ContactPn", contactPerson));
            bins.add(new Bin("ContactPnPhone", contactPersonPhone));
            bins.add(new Bin("ContactPnEmail", contactPersonEmail));
            bins.add(new Bin("BusiLic", businessLicense));
            bins.add(new Bin("BusiLicIssDate", businessLicenseIssueDate));
            bins.add(new Bin("BusiLicIssBy", businessLicenseIssueBy));
            bins.add(new Bin("Mod", moderator));
            bins.add(new Bin("ModLicense", moderatorLicense));
            bins.add(new Bin("ModLicIssDate", moderatorLicenseIssueDate));
            bins.add(new Bin("ModLicExpDate", moderatorLicenseExpDate));
            bins.add(new Bin("TaxCode", taxCode));
            bins.add(new Bin("Director", director));
            bins.add(new Bin("Fax", fax));
            bins.add(new Bin("Website", website));
            bins.add(new Bin("CompanyPhone", companyPhone));
            bins.add(new Bin("TranLic", businessTransportLicense));
            bins.add(new Bin("TranLicIssDate", businessTransportLicenseIssueDate));
            bins.add(new Bin("TranLicExpDate", businessTransportLicenseExpDate));
            bins.add(new Bin("Nationality", nationality));
            bins.add(new Bin("LicenseNo", licenseNo));
            bins.add(new Bin("IssueDate", issueDate));
            bins.add(new Bin("IssueBy", issueBy));
            bins.add(new Bin("Gender", gender));
            bins.add(new Bin("Ethnic", ethnic));
            bins.add(new Bin("OwnerType", vehicleOwnerType));
            bins.add(new Bin("ContactAddress", (contactAddress)));
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
            this.contactPhone = (String) map.get("ContactPhone");
            this.contactPersonPhone = (String) map.get("ContactPnPhone");
            this.contactPerson = (String) map.get("ContactPn");
            this.contactPersonEmail = (String) map.get("ContactPnEmail");
            this.businessLicense = (String) map.get("BusiLic");
            this.businessLicenseIssueDate = (Long) map.get("BusiLicIssDate");
            this.businessLicenseIssueBy = (String) map.get("BusiLicIssBy");
            this.moderator = (String) map.get("Mod");
            this.moderatorLicense = (String) map.get("ModLicense");
            this.moderatorLicenseIssueDate = (Long) map.get("ModLicIssDate");
            this.moderatorLicenseExpDate = (Long) map.get("ModLicExpDate");
            this.email = (String) map.get("Email");
            this.director = (String) map.get("Director");
            this.taxCode = (String) map.get("TaxCode");
            this.fax = (String) map.get("Fax");
            this.website = (String) map.get("Website");
            this.companyPhone = (String) map.get("CompanyPhone");
            this.businessTransportLicense = (String) map.get("TranLic");
            this.businessTransportLicenseIssueDate = (Long) map.get("TranLicIssDate");
            this.businessTransportLicenseExpDate = (Long) map.get("TranLicExpDate");
            this.nationality = (String) map.get("Nationality");
            this.licenseNo = (String) map.get("LicenseNo");
            this.issueDate = (Long) map.get("IssueDate");
            this.issueBy = (String) map.get("IssueBy");
            this.gender = (Long) map.get("Gender");
            this.vehicleOwnerType = (Long) map.get("OwnerType");
            this.ethnic = (String) map.get("Ethnic");
            Map<String, String> contactAddressMap = (Map<String, String>) map.get("ContactAddress");
            this.contactAddress = contactAddressMap == null ? new HashMap<>() : contactAddressMap;
            Map<String, String> addressMap = (Map<String, String>) map.get("Address");
            this.address = addressMap == null ? new HashMap<>() : addressMap;
            Map<String, List<String>> attachPropertiesMap = (Map<String, List<String>>) map.get("AttachProp");
            this.attachProperties = attachPropertiesMap == null ? new HashMap<>() : attachPropertiesMap;
            return true;
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return false;
    }

    public boolean parseBankAccount(Record record) {
        try {

        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        return false;
    }

}
