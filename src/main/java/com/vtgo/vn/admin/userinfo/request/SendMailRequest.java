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
 * @author tvhdh
 */
public class SendMailRequest extends BaseRequest<Object> implements Serializable {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
