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
 * @author tvhdh
 */
public class Policy extends BaseRequest<Object> implements BaseObject {

    private static final Logger log = Logger.getLogger(Policy.class);

    private Long policyId;
    private Double ratioVat;
    private Double constant;
    private Double ratiRoseNoVat;
    private Double ratioRoseVat;
    private Double ratioVatTax;
    private Double ratioPerTax;
    private String description;
//    private Long pk;

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Double getRatioVat() {
        return ratioVat;
    }

    public void setRatioVat(Double ratioVat) {
        this.ratioVat = ratioVat;
    }

    public Double getConstant() {
        return constant;
    }

    public void setConstant(Double constant) {
        this.constant = constant;
    }

    public Double getRatiRoseNoVat() {
        return ratiRoseNoVat;
    }

    public void setRatiRoseNoVat(Double ratiRoseNoVat) {
        this.ratiRoseNoVat = ratiRoseNoVat;
    }

    public Double getRatiRoseVat() {
        return ratioRoseVat;
    }

    public void setRatiRoseVat(Double ratiRoseVat) {
        this.ratioRoseVat = ratiRoseVat;
    }

    public Double getRatioVatTax() {
        return ratioVatTax;
    }

    public void setRatioVatTax(Double ratioVatTax) {
        this.ratioVatTax = ratioVatTax;
    }

    public Double getRatioPerTax() {
        return ratioPerTax;
    }

    public void setRatioPerTax(Double ratioPerTax) {
        this.ratioPerTax = ratioPerTax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Long getPk() {
//        return pk;
//    }
//
//    public void setPk(Long pk) {
//        this.pk = pk;
//    }

    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.policyId = record.getLong("PolicyId");
            this.ratioVat = record.getDouble("RatioVat");
            this.constant = record.getDouble("Constant");
            this.ratiRoseNoVat = record.getDouble("RatiRoseNoVat");
            this.ratioRoseVat = record.getDouble("RatioRoseVat");
            this.ratioVatTax = record.getDouble("RatioVatTax");
            this.ratioPerTax = record.getDouble("RatioPerTax");
            this.description = record.getString("Description");
//            this.pk = record.getLong("PK");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @JsonIgnore
    @Override
    public boolean parse(Map map) {
        try {
            this.policyId = (Long) map.get("PolicyId");
            this.ratioVat = (Double) map.get("RatioVat");
            this.constant = (Double) map.get("Constant");
            this.ratiRoseNoVat = (Double) map.get("RatiRoseNoVat");
            this.ratioRoseVat = (Double) map.get("RatioRoseVat");
            this.ratioVatTax = (Double) map.get("RatioVatTax");
            this.ratioPerTax = (Double) map.get("RatioPerTax");
            this.description = (String) map.get("Description");
//            this.pk = (Long) map.get("PK");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

    }

    @JsonIgnore
    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bins = new ArrayList<>();
            bins.add(new Bin("PolicyId", policyId));
            bins.add(new Bin("RatioVat", ratioVat));
            bins.add(new Bin("Constant", constant));
            bins.add(new Bin("RatiRoseNoVat", ratiRoseNoVat));
            bins.add(new Bin("RatioRoseVat", ratioRoseVat));
            bins.add(new Bin("RatioVatTax", ratioVatTax));
            bins.add(new Bin("RatioPerTax", ratioPerTax));
            bins.add(new Bin("Description", description));
//            bins.add(new Bin("PK", pk));
            return bins.toArray(new Bin[bins.size()]);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
