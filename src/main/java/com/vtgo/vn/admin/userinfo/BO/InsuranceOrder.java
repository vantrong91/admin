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
public class InsuranceOrder extends BaseRequest<Object> implements BaseObject {

    private static final Logger logger = Logger.getLogger(InsuranceOrder.class);
    private Long accountId;
    private String orderId;
    private Long sumInsuPrice;
    private String contractNo;
    private Long insuranPrice;
    private Long insuranSpend;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getSumInsuPrice() {
        return sumInsuPrice;
    }

    public void setSumInsuPrice(Long sumInsuPrice) {
        this.sumInsuPrice = sumInsuPrice;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getInsuranPrice() {
        return insuranPrice;
    }

    public void setInsuranPrice(Long insuranPrice) {
        this.insuranPrice = insuranPrice;
    }

    public Long getInsuranSpend() {
        return insuranSpend;
    }

    public void setInsuranSpend(Long insuranSpend) {
        this.insuranSpend = insuranSpend;
    }
    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.accountId = record.getLong("AccountId");
            this.contractNo = record.getString("ContractNo");
            this.insuranPrice = record.getLong("InsuranPrice");
            this.insuranSpend = record.getLong("InsuranSpend");
            this.orderId = record.getString("OrderId");
            this.sumInsuPrice = record.getLong("SumInsuPrice");
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
        return true;
    }
    @JsonIgnore
    @Override
    public boolean parse(Map map) {
        try {
            this.accountId = (Long) map.get("AccountId");
            this.contractNo = (String) map.get("ContractNo");
            this.insuranPrice = (Long) map.get("InsuranPrice");
            this.insuranSpend = (Long) map.get("InsuranSpend");
            this.orderId = (String) map.get("OrderId");
            this.sumInsuPrice = (Long) map.get("SumInsuPrice");
            return true;
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
    }
    @JsonIgnore
    @Override
    public Bin[] toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("AccountId", accountId));
        bins.add(new Bin("OrderId", orderId));
        bins.add(new Bin("SumInsuPrice", sumInsuPrice));
        bins.add(new Bin("ContractNo", contractNo));
        bins.add(new Bin("InsuranPrice", insuranPrice ));
        bins.add(new Bin("InsuranSpend", insuranSpend));
        return bins.toArray(new Bin[bins.size()]);
    }

}
