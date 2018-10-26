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
 * @author viett
 */
public class OrderCompleteRequest extends BaseRequest<Object> implements Serializable{
    private String orderId;
    private String message;
    private Long paid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "OrderCompleteRequest{" + "orderId=" + orderId + ", message=" + message + ", paid=" + paid + '}';
    }
    
}
