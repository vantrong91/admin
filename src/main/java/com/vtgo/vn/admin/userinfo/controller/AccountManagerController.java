/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.listener.ExecuteListener;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.AccountManager;
import com.vtgo.vn.admin.userinfo.BO.Account;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.AccountManagerService;
import com.vtgo.vn.admin.util.SecurityUtils;
import com.vtgo.vn.admin.util.SequenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author viett
 */
@Service
@AllArgsConstructor
public class AccountManagerController extends BaseController implements AccountManagerService {

    private static final Logger log = Logger.getLogger(AccountManagerController.class.getName());

    @Override
    public ResponseEntity searchAccountMan(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchVal = request.getSearchParam();
            if (searchVal != null && !searchVal.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "FullName");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "Email");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "AccountId");
            s.put("order", "DESC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        AccountManager accountManager = new AccountManager();
                        if (accountManager.parse((Map) o)) {
                            listAcc.add(accountManager);
                        }
                    }
                }
            }
            response.setData(listAcc);
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getAccountManById(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                AccountManager accountManager = new AccountManager();
                accountManager.parse(rec);
                response.setData(Arrays.asList(accountManager));
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity create(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, "Email", "EmailIdx", request.getEmail());
            if (rs != null && rs.iterator().hasNext()) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Email đã được sử dụng");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, "PhoneNumber", "PhoneNumberIdx", request.getPhoneNumber());
            if (rs != null & rs.iterator().hasNext()) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Số điện thoại đã được sử dụng");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            long accountId = SequenceManager.getInstance().getSequence(Account.class.getSimpleName());
            if (accountId <= 0) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Get accoundId squence error");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            //TODO create account
            List<Bin> lstBin = new ArrayList();
            lstBin.add(new Bin("AccountId", accountId));
            String password = request.getPassword();
            String salt = SecurityUtils.createSalt();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            lstBin.add(new Bin("Password", bCryptPasswordEncoder.encode(password)));
            lstBin.add(new Bin("Salt", salt));
            lstBin.add(new Bin("Email", request.getEmail()));
            lstBin.add(new Bin("FullName", request.getFullName()));
            lstBin.add(new Bin("PhoneNumber", request.getPhoneNumber()));
            lstBin.add(new Bin("AccountType", request.getAccountType()));
            String accountCode = "US" + request.getPhoneNumber();
            lstBin.add(new Bin("AccountCode", accountCode));
            try {
               update(AerospikeFactory.getInstance().onlyCreatePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, accountId, lstBin.toArray(new Bin[lstBin.size()]));
            } catch (Exception e) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response.setData(Arrays.asList(request));
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e,e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
