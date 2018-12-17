/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.BalanceTemp;
import com.vtgo.vn.admin.userinfo.BO.Transaction;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Admin
 */
public interface BalanceService {

    public ResponseEntity searchBalance(SearchRequest request);

    public ResponseEntity searchAccountBalance(SearchRequest request);

    public ResponseEntity getBalanceId(BalanceTemp request);

    public ResponseEntity transaction(Transaction request);

}
