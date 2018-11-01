/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.AccountManager;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.AccountManagerService;
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
 * @author viett
 */
@RestController
@RequestMapping("v1/account-man")
@AllArgsConstructor
public class AccounManagerApi {

    private static final Logger log = Logger.getLogger(AccounManagerApi.class.getName());
    private final AccountManagerService accountManagerService;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest search) {
        log.debug(search.getSearchParam());
        return accountManagerService.searchAccountMan(search);
    }

    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody AccountManager request) {
        log.debug("request /getById: " + request.getAccountId());
        return accountManagerService.getAccountManById(request);
    }
    
    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logout(@RequestBody AccountManager request){
        log.debug("request /logout: " + request.getAccountId());
        return accountManagerService.logout(request);
    }
    
    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity create(@RequestBody AccountManager request){
        log.debug("request /create: " + request);
        return accountManagerService.create(request);
    }
    
    @PostMapping(value = "check-login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity checkLogin(@RequestBody AccountManager request){
        log.debug("request /checkLogin: " + request);
        return accountManagerService.checkLogin(request);
    }
    
    @PostMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody AccountManager request){
        log.debug("request /delete: " + request);
        return accountManagerService.delete(request);
    }
    
    @PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody AccountManager request){
        log.debug("request /update" + request);
        return accountManagerService.update(request);
    }

}
