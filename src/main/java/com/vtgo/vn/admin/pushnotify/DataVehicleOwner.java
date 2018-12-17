/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

import java.util.List;

/**
 *
 * @author Trong Van
 */
public class DataVehicleOwner {

     private String type;//// 0 Lái xe kết nối với xe, 1 : Có phản hồi mới cho đơn hàng, 2 : Lái xe đã báo đơn hàng, 3 : xe hết hạn đăng kiểm
//    private Long accDriverId;
    private List<Long> lstVehicleId;
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

    public List<Long> getLstVehicleId() {
        return lstVehicleId;
    }

    public void setLstVehicleId(List<Long> lstVehicleId) {
        this.lstVehicleId = lstVehicleId;
    }

    public String getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(String orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
