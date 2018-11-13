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
public class SaltRequest extends BaseRequest<Object> implements Serializable {

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
