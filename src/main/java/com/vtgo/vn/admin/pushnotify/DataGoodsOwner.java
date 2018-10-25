/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

/**
 *
 * @author Trong Van
 */
public class DataGoodsOwner {

    private String type;//// 0 cho thông báo báo giá mới, 1 : lái xe thông báo sự cố, 2 : thông báo chương trình khuyến mãi
    private String orderId;
    private String quotationId;
    private String loadingCode;
    private String msg;
    private String promotionCode;//"DEDANG"// với type là chương trình km
    private boolean urgent;//// true với trường hợp thông báo sự cố khẩn, false thông báo sự cố bình thường
    private Long acctIdDriver;
    private String nameDr;
    private String phoneNumberDr;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLoadingCode() {
        return loadingCode;
    }

    public void setLoadingCode(String loadingCode) {
        this.loadingCode = loadingCode;
    }

    public Long getAcctIdDriver() {
        return acctIdDriver;
    }

    public void setAcctIdDriver(Long acctIdDriver) {
        this.acctIdDriver = acctIdDriver;
    }

    public String getNameDr() {
        return nameDr;
    }

    public void setNameDr(String nameDr) {
        this.nameDr = nameDr;
    }

    public String getPhoneNumberDr() {
        return phoneNumberDr;
    }

    public void setPhoneNumberDr(String phoneNumberDr) {
        this.phoneNumberDr = phoneNumberDr;
    }

}
