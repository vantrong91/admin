/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.BalanceHis;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author viett
 */
public interface BalanceHisService {

    public ResponseEntity searchBalanceHis(SearchRequest request);

    public ResponseEntity getBalanceHisById(BalanceHis request);

    public ResponseEntity getBalanceHisByAccId(BalanceHis request);

    public ResponseEntity update(BalanceHis request);

    public ResponseEntity create(BalanceHis request);

    public ResponseEntity delete(BalanceHis request);
}
