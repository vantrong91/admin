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
public class MsgNotifyGoodsOwner {

    private TitleObj notification;
    private DataGoodsOwner data;

    public TitleObj getNotification() {
        return notification;
    }

    public void setNotification(TitleObj notification) {
        this.notification = notification;
    }

    public DataGoodsOwner getData() {
        return data;
    }

    public void setData(DataGoodsOwner data) {
        this.data = data;
    }

}
