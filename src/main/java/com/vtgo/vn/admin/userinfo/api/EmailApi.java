/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.request.SaltRequest;
import com.vtgo.vn.admin.userinfo.request.SendMailRequest;
import com.vtgo.vn.admin.userinfo.service.EmailService;
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
@RequestMapping("v1/email")
@AllArgsConstructor
public class EmailApi {

    private static final Logger LOGGER = Logger.getLogger(EmailApi.class);
    private final EmailService emailService;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity send(@RequestBody SendMailRequest request) {
        LOGGER.debug("request /sendEmail: " + request);
        return emailService.sendEmail(request);
    }
    
    @PostMapping(value = "/reset-pass", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity resetPass(@RequestBody SaltRequest request) {
        LOGGER.debug("request /resetPassword: " + request);
        return emailService.resetPassword(request);
    }
}
