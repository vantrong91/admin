/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.AerospikeException;
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
public class TransferFee implements BaseObject, Serializable {

    public static Logger log = Logger.getLogger(TransferFee.class.getName());
    private Long transferId;
    private Long bankCode;
    private String bankName;
    private Double fee;
    private String linkIB;
    private Long connect;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getBankCode() {
        return bankCode;
    }

    public void setBankCode(Long bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getLinkIB() {
        return linkIB;
    }

    public void setLinkIB(String linkIB) {
        this.linkIB = linkIB;
    }

    public Long getConnect() {
        return connect;
    }

    public void setConnect(Long connect) {
        this.connect = connect;
    }

    @Override
    public boolean parse(Record record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean parse(Map<String, Object> map) {
        try {
            this.transferId = (Long) map.get("TransferId");
            this.bankCode = (Long) map.get("BankCode");
            this.bankName = (String) map.get("BankName");
            this.connect = (Long) map.get("Connect");
            this.fee = Double.parseDouble(String.valueOf(map.get("Fee")));
            this.linkIB = (String) map.get("LinkIB");
            return true;
        } catch (AerospikeException ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bins = new ArrayList<>();
            bins.add(new Bin("TransferId", transferId));
            bins.add(new Bin("BankCode", bankCode));
            bins.add(new Bin("BankName", bankName));
            bins.add(new Bin("Connect", connect));
            bins.add(new Bin("Fee", fee));
            bins.add(new Bin("LinkIB", linkIB));

            return bins.toArray(new Bin[bins.size()]);
        } catch (AerospikeException ex) {
            log.debug(ex.getMessage());
            return null;
        }
    }

}
