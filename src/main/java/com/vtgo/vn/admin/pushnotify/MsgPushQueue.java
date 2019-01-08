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
public class MsgPushQueue<T> {

    private Integer typeReceive;
    private Integer typeSend;
    private List<T> data;
    private NotificationObject<EmailObject> notificationObect;

    public Integer getTypeReceive() {
        return typeReceive;
    }

    public void setTypeReceive(Integer typeReceive) {
        this.typeReceive = typeReceive;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTypeSend() {
        return typeSend;
    }

    public void setTypeSend(Integer typeSend) {
        this.typeSend = typeSend;
    }

    public NotificationObject<EmailObject> getNotificationObect() {
        return notificationObect;
    }

    public void setNotificationObect(NotificationObject<EmailObject> notificationObect) {
        this.notificationObect = notificationObect;
    }

}
