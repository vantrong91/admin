/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Driver;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author HP
 */
public interface DriverService {

    public ResponseEntity searchDriver(SearchRequest request);

    public ResponseEntity getDriverById(Driver request);

    public ResponseEntity update(Driver request);

    public ResponseEntity create(Driver request);

    public ResponseEntity delete(Driver request);
}
