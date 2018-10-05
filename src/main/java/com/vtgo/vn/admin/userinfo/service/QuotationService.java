/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Quotation;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Admin
 */
public interface QuotationService {
    public ResponseEntity searchQuotation(SearchRequest request);

    public ResponseEntity getQuotationId(Quotation request);

    public ResponseEntity update(Quotation request);

    public ResponseEntity create(Quotation request);
    
    public ResponseEntity delete(Quotation request);
}
