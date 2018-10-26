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
public class MsgVehicleOwner {

    private TitleObj notification;
    private DataVehicleOwner data;

    public TitleObj getNotification() {
        return notification;
    }

    public void setNotification(TitleObj notification) {
        this.notification = notification;
    }

    public DataVehicleOwner getData() {
        return data;
    }

    public void setData(DataVehicleOwner data) {
        this.data = data;
    }

}
