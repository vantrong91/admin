/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.GoodOwner;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author HP
 */
public interface GoodOwnerService {

    public ResponseEntity searchGoodOwner(SearchRequest request);

    public ResponseEntity getGoodOwnerById(GoodOwner request);

    public ResponseEntity update(GoodOwner request);

    public ResponseEntity create(GoodOwner request);
    
    public ResponseEntity delete(GoodOwner request);
}
