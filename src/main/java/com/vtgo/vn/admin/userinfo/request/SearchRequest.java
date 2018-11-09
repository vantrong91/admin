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
 * @author HP
 */
public class SearchRequest extends BaseRequest<Object> implements Serializable {
    private String searchParam;
    private Long searchParam2;

    public Long getSearchParam2() {
        return searchParam2;
    }

    public void setSearchParam2(Long searchParam2) {
        this.searchParam2 = searchParam2;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
    
}
