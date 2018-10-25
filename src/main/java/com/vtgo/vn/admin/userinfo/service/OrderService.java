/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Order;
import com.vtgo.vn.admin.userinfo.request.OrderCompleteRequest;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author viett
 */
public interface OrderService {

    public ResponseEntity searchOrder(SearchRequest request);

    public ResponseEntity getOrderById(Order request);

    public ResponseEntity update(Order request);

    public ResponseEntity create(Order request);

    public ResponseEntity delete(Order request);
    
    public ResponseEntity completeOrder(OrderCompleteRequest request);
}
