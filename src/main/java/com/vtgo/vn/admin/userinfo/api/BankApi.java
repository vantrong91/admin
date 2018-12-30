/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.Bank;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BankService;
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
@RequestMapping("v1/bank")
@AllArgsConstructor
public class BankApi {

    private final BankService bankService;
    private static final Logger log = Logger.getLogger(BankApi.class.getName());

    @PostMapping(value = "search-bank-ad", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity searchBankAd(@RequestBody SearchRequest request) {
        log.debug(request.toString());
        return bankService.searchBankAdminList(request);
    }

    @PostMapping(value = "create-bank-ad", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity ceateBankAdmin(@RequestBody Bank request) {
        return bankService.ceateBankAdmin(request);
    }

    @PostMapping(value = "delete-bank-ad", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteBankAdmin(@RequestBody Bank request) {
        return bankService.deleteBankAdmin(request);
    }

    @PostMapping(value = "update-bank-ad", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateBankAdmin(@RequestBody Bank request) {
        return bankService.updateBankAdmin(request);
    }
}
