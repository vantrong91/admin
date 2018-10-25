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
public class MsgNotifyDriver {

    private TitleObj notification;
    private DataDriver data;

    public TitleObj getNotification() {
        return notification;
    }

    public void setNotification(TitleObj notification) {
        this.notification = notification;
    }

    public DataDriver getData() {
        return data;
    }

    public void setData(DataDriver data) {
        this.data = data;
    }
    

}
