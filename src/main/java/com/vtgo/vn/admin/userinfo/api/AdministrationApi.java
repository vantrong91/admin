/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.Administration;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.AdministrationService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tvhdh
 */

@RestController
@RequestMapping("v1/administration")
@AllArgsConstructor

public class AdministrationApi {
    private static final Logger log = Logger.getLogger(AdministrationApi.class.getName());
    private final AdministrationService admService;
    
    @PostMapping(value = "/getProvince", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest request){
        log.debug("request /getProvince: " + request);
        return admService.getProvince(request);
    }
    
    @PostMapping(value ="/getById", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody Administration request){
        log.debug("request /getById: " +  request.getPk());
        return admService.getById(request);
    }
    
    
    
}
