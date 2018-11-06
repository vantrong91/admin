/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.InsuranceOrder;
import com.vtgo.vn.admin.userinfo.request.InsuOrderCompleteRequest;
import com.vtgo.vn.admin.userinfo.request.OrderCompleteRequest;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.InsuranceOrderService;

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
@RequestMapping("v1/insurance-order")
@AllArgsConstructor
public class InsuranceOrderApi {
    private static final Logger LOGGER = Logger.getLogger(InsuranceOrderApi.class.getName());
    private final InsuranceOrderService insuranceOrderService;
    
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest request){
        LOGGER.debug("request /searchInsuOrder: " + request);
        return insuranceOrderService.searchInsuOrder(request);
    }
    
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity create(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /create: " + request);
        return insuranceOrderService.create(request);
    }
    
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /update: " + request);
        return insuranceOrderService.update(request);
    }
    
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /delete: " + request);
        return insuranceOrderService.delete(request);
    }
    
    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /getById: " + request);
        return insuranceOrderService.getById(request);
    }
    
    @PostMapping(value = "/get-goodowner-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getGoodOwnerById(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /getGoodOwnerById: " + request);
        return insuranceOrderService.getGoodsOwnerById(request);
    }
    
    @PostMapping(value = "/complete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity complete(@RequestBody InsuOrderCompleteRequest request){
        LOGGER.debug("request /complete" + request);
        return insuranceOrderService.completeInsuOrder(request);
    }
    
    @PostMapping(value = "/getComplete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getComplete(@RequestBody InsuranceOrder request){
        LOGGER.debug("request /getComplete" + request);
        return insuranceOrderService.getComplete(request);
    }
}
