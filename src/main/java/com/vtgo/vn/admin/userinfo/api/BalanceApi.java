/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;


import com.vtgo.vn.admin.userinfo.BO.BalanceTemp;
import com.vtgo.vn.admin.userinfo.BO.Quotation;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceService;
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
 * @author Admin
 */
@RestController
@RequestMapping("v1/balance")
@AllArgsConstructor
public class BalanceApi {
    private static final Logger LOGGER = Logger.getLogger(BalanceApi.class.getName());
    private final BalanceService balanceService;
    
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest request) {
        LOGGER.debug("request /searchBalance: " + request);
        return balanceService.searchBalance(request);
    }
    
    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody BalanceTemp request) {
        LOGGER.debug("request /getById: " + request);
        return balanceService.getBalanceId(request);
    }
    
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody BalanceTemp request) {
        LOGGER.debug("request /update: " + request);
        return balanceService.update(request);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity create(@RequestBody BalanceTemp request) {
        LOGGER.debug("request /create: " + request);
        return balanceService.create(request);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody BalanceTemp request) {
        LOGGER.debug("request /delete: " + request);
        return balanceService.delete(request);
    }
}
