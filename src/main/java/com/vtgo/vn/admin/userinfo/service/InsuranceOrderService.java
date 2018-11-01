/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;
import com.vtgo.vn.admin.userinfo.BO.InsuranceOrder;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author tvhdh
 */
public interface InsuranceOrderService {
    public ResponseEntity searchInsuOrder(SearchRequest request);
    
    public ResponseEntity create(InsuranceOrder request);
    
    public ResponseEntity update(InsuranceOrder request);
    
    public ResponseEntity delete(InsuranceOrder request);
    
    public ResponseEntity getById(InsuranceOrder request);
    
    public ResponseEntity getGoodsOwnerById(InsuranceOrder request);
}
