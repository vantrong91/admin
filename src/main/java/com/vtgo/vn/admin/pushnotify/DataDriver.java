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
public class DataDriver {

    private String type;// 0 có đơn hàng mới, 1 : chủ hàng phản hồi báo giá, 2 : chủ hàng từ chối báo giá, 3 : Đơn hàng cần xác định chuyến
    private String orderId;
    private String orderIdOld;
    private String quotationId;
    private String msg;

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

    public String getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(String orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

}
