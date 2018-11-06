/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.request;

import com.vtgo.vn.admin.base.BaseRequest;
import java.io.Serializable;

/**
 *
 * @author tvhdh
 */
public class InsuOrderCompleteRequest extends BaseRequest<Object> implements  Serializable{
    private String orderId;
    private Long accountId;
    private String message;
    private Long sumInsuPrice;
    private String contractNo;
    private Long insuranPrice;
    private Long insuranSpend;
    private Integer state;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    private Long updateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
