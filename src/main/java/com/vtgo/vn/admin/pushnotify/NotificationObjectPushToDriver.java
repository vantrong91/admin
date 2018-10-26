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
public class NotificationObjectPushToDriver {

    private Long accountId;
    private int notifyType;
    private MsgNotifyDriver message;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public MsgNotifyDriver getMessage() {
        return message;
    }

    public void setMessage(MsgNotifyDriver message) {
        this.message = message;
    }

}
