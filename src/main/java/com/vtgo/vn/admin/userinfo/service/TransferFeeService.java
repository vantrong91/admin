/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.TransferFee;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author viett
 */
public interface TransferFeeService {

    public ResponseEntity search(SearchRequest request);

    public ResponseEntity getConnect(TransferFee requeset);

    public ResponseEntity update(TransferFee request);

    public ResponseEntity delete(TransferFee request);

    public ResponseEntity create(TransferFee request);
}
