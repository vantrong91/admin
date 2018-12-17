/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

import com.vtgo.vn.admin.userinfo.BO.Order;
import java.util.List;

/**
 *
 * @author Trong Van
 */
public class DataDriver {

      private String type;// 0 có đơn hàng mới, 1 : chủ hàng phản hồi báo giá, 2 : chủ hàng từ chối báo giá, 3 : Đơn hàng cần xác định chuyến
    private String orderId;
    private String orderIdOld;
    private String quotationId;
    private Long vehicleId;
    private String msg;
    private String loadingCode;
    private Long driverId;
    private String password;
    private String driverCode;
    private Long deliverTime;
    private Long toDeliverTime;
    private Long receiveTime;
    private Long toReceiveTime;
    private String srcAddress;
    private String dstAddress;
    private Long startTime;
    private Long endTime;
    private Long routeId;
    private List<Order> orderList;

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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLoadingCode() {
        return loadingCode;
    }

    public void setLoadingCode(String loadingCode) {
        this.loadingCode = loadingCode;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public Long getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Long deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Long getToDeliverTime() {
        return toDeliverTime;
    }

    public void setToDeliverTime(Long toDeliverTime) {
        this.toDeliverTime = toDeliverTime;
    }

    public Long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Long getToReceiveTime() {
        return toReceiveTime;
    }

    public void setToReceiveTime(Long toReceiveTime) {
        this.toReceiveTime = toReceiveTime;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

   
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}
