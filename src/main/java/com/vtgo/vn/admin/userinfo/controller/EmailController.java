/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Account;
import com.vtgo.vn.admin.userinfo.BO.AccountManager;
import com.vtgo.vn.admin.userinfo.BO.User;
import com.vtgo.vn.admin.userinfo.request.SaltRequest;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.request.SendMailRequest;
import com.vtgo.vn.admin.userinfo.service.EmailService;
import com.vtgo.vn.admin.userinfo.service.sendEmailService;
import com.vtgo.vn.admin.util.SecurityUtils;
import com.vtgo.vn.admin.util.SequenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@AllArgsConstructor
public class EmailController extends BaseController implements EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailController.class.getName());
    private JavaMailSender javaMailSender;

    @Override
    public ResponseEntity sendEmail(SendMailRequest request) {
        String email = request.getEmail();
        // lấy dữ liệu từ mail
//        SearchRequest requestEmail = new SearchRequest();
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        Map<String, Object> argument = new HashMap<>();
        List<Value.MapValue> argumentFilter = new ArrayList<>();
        String searchVal = email;
        if (searchVal != null && !searchVal.isEmpty()) {
            Map<String, Object> f = new HashMap<>();
            f.put("field", "Email");
            f.put("value", searchVal);
            f.put("operator", "=");
            argumentFilter.add(new Value.MapValue(f));
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "AccountId");
            s.put("order", "DESC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));
            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE,
                            DatabaseConstants.ACCOINT_MAN_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        AccountManager accountManager = new AccountManager();
                        if (accountManager.parse((Map) o)) {
                            accountManager.getPassword();
                            listAcc.add(accountManager);
                        }
                    }
                }
            }
        }
        response.setData(listAcc);
        LOGGER.info(listAcc);
        String code = listAcc.get(0).getSalt();
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("namhanyu@gmail.com");
        mail.setSubject("Email xác minh tài khoản");
        mail.setText("Mã xác minh tài khoản của bạn là: " + code);

        javaMailSender.send(mail);

        response.setStatus(ResponseConstants.SUCCESS);
        response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Override
    public ResponseEntity resetPassword(SaltRequest request) {
        String salt = request.getSalt();
        
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        Map<String, Object> argument = new HashMap<>();
        List<Value.MapValue> argumentFilter = new ArrayList<>();
        String searchVal = salt;
        if (searchVal != null && !searchVal.isEmpty()) {
            Map<String, Object> f = new HashMap<>();
            f.put("field", "Salt");
            f.put("value", searchVal);
            f.put("operator", "=");
            argumentFilter.add(new Value.MapValue(f));
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "AccountId");
            s.put("order", "DESC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));
            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE,
                            DatabaseConstants.ACCOINT_MAN_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        AccountManager accountManager = new AccountManager();
                        if (accountManager.parse((Map) o)) {
                            accountManager.getPassword();
                            listAcc.add(accountManager);
                        }
                    }
                }
            }
        }
        response.setData(listAcc);
        LOGGER.info(listAcc);
//         update account
        AccountManager requestEmail = new AccountManager();
        requestEmail.setAccountId(listAcc.get(0).getAccountId());
        requestEmail.setAccountCode(listAcc.get(0).getAccountCode());
        requestEmail.setAccountToken(listAcc.get(0).getAccountToken());
        requestEmail.setAccountType(listAcc.get(0).getAccountType());
        requestEmail.setDeviceToken(listAcc.get(0).getDeviceToken());
        requestEmail.setEmail(listAcc.get(0).getEmail());
        requestEmail.setFullName(listAcc.get(0).getFullName());
        requestEmail.setOsType(listAcc.get(0).getOsType());
        requestEmail.setPassword(listAcc.get(0).getPassword());
        requestEmail.setPhoneNumber(listAcc.get(0).getPhoneNumber());
        requestEmail.setSalt(listAcc.get(0).getSalt());
        requestEmail.setUserId(listAcc.get(0).getUserId());
        
        BaseResponse responseEmail = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, requestEmail.getAccountId());
            if (rec != null) {
                String password = "123456";
                String saltNew = SecurityUtils.createSalt();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                requestEmail.setPassword(bCryptPasswordEncoder.encode(password));
                requestEmail.setSalt(saltNew);
                update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, requestEmail.getAccountId(), requestEmail.toBins());
                responseEmail.setStatus(ResponseConstants.SUCCESS);
                responseEmail.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                responseEmail.setStatus(ResponseConstants.SERVICE_ERROR);
                responseEmail.setMessage(ResponseConstants.SERVICE_ACCOUNT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            responseEmail.setStatus(ResponseConstants.SERVICE_FAIL);
            responseEmail.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(responseEmail);
        }
    }

}
