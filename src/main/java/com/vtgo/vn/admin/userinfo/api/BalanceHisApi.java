/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.BalanceHis;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceHisService;
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
@RequestMapping("v1/balance-his")
@AllArgsConstructor
public class BalanceHisApi {

    private static final Logger log = Logger.getLogger(BalanceHisApi.class.getName());
    private final BalanceHisService balance;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest search) {
        log.debug(search.getSearchParam());
        return balance.searchBalanceHis(search);
    }

    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getBalanceHisById(@RequestBody BalanceHis search) {
        log.debug(search.toString());
        return balance.getBalanceHisById(search);
    }

    @PostMapping(value = "/get-by-acc-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getBalanceHisByAccId(@RequestBody BalanceHis search) {
        log.debug(search.toString());
        return balance.getBalanceHisByAccId(search);
    }
}
