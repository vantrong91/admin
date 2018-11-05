/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.Policy;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vtgo.vn.admin.userinfo.service.PolicyService;

/**
 *
 * @author tvhdh
 */
@RestController
@RequestMapping("v1/policy")
@AllArgsConstructor
public class PolicyApi {

    private static final Logger LOGGER = Logger.getLogger(PolicyApi.class.getName());
    private final PolicyService policyService;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest request) {
        LOGGER.debug("request /searchPolicy: " + request);
        return policyService.search(request);
    }

    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody Policy request) {
        LOGGER.debug("request /getById: " + request);
        return policyService.getById(request);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody Policy request) {
        LOGGER.debug("request /update: " + request);
        return policyService.update(request);
    }
    
    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity create(@RequestBody Policy request){
        LOGGER.debug("request /create: " + request);
        return policyService.create(request);
    }
    
    @PostMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody Policy request){
        LOGGER.debug("request /delete: " +  request);
        return policyService.delete(request);
    }
}
