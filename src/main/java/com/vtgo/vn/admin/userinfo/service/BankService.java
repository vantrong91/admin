/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Bank;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author viett
 */
public interface BankService {

    public ResponseEntity searchBankAdminList(SearchRequest request);

    public ResponseEntity getByBankCode(SearchRequest request);

    public ResponseEntity ceateBankAdmin(Bank request);

    public ResponseEntity deleteBankAdmin(Bank request);

    public ResponseEntity updateBankAdmin(Bank request);
}
