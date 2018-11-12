/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.request.SaltRequest;
import com.vtgo.vn.admin.userinfo.request.SendMailRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Admin
 */
public interface EmailService {
    public ResponseEntity sendEmail(SendMailRequest request);
    
    public ResponseEntity resetPassword(SaltRequest request);
}
