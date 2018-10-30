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
public class Category extends BaseRequest<Object> implements BaseObject{
    private static final Logger logger = Logger.getLogger(Category.class);
        private Long pk;
        private Long id_cha;
        private Long type;
        private String item;
        private Long feeCharge;
        private Long latefine;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public Long getId_cha() {
        return id_cha;
    }

    public void setId_cha(Long id_cha) {
        this.id_cha = id_cha;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getFeeCharge() {
        return feeCharge;
    }

    public void setFeeCharge(Long feeCharge) {
        this.feeCharge = feeCharge;
    }

    public Long getLatefine() {
        return latefine;
    }

    public void setLatefine(Long latefine) {
        this.latefine = latefine;
    }
    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.pk = record.getLong("PK");
            this.id_cha = record.getLong("ID_CHA");
            this.type = record.getLong("Type");
            this.item = record.getString("Item");
            this.feeCharge = record.getLong("FeeCharge");
            this.latefine = record.getLong("Latefine");
        } catch (Exception e) {
            logger.error(e,e);
            return false;
        }
        return true;
    }
    @JsonIgnore
    @Override
    public boolean parse(Map map) {
        try {
            this.pk = (Long) map.get("PK");
            this.id_cha = (Long) map.get("ID_CHA");
            this.type = (Long) map.get("Type");
            this.item = (String) map.get("Item");
            this.feeCharge = (Long) map.get("FeeCharge");
            this.latefine = (Long) map.get("Latefine");
            return true;
        } catch (Exception e) {
            logger.error(e,e);
            return false;
        }
    }
    @JsonIgnore
    @Override
    public Bin[] toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("PK", pk));
        bins.add(new Bin("ID_CHA", id_cha));
        bins.add(new Bin("Type", type));
        bins.add(new Bin("Item", item));
        bins.add(new Bin("FeeCharge", feeCharge));
        bins.add(new Bin("Latefine", latefine));
        return bins.toArray(new Bin[bins.size()]);
    }
}
