/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Administration;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author tvhdh
 */
public interface AdministrationService {

    public ResponseEntity getProvince(SearchRequest request);

    public ResponseEntity getById(Administration request);

}
