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
public class DataVehicleOwner {

    private String type;//// 0 Lái xe kết nối với xe, 1 : Có phản hồi mới cho đơn hàng, 2 : Lái xe đã báo đơn hàng, 3 : xe hết hạn đăng kiểm
    private Long accDriverId;
    private Long vehicleId;
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccDriverId() {
        return accDriverId;
    }

    public void setAccDriverId(Long accDriverId) {
        this.accDriverId = accDriverId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
