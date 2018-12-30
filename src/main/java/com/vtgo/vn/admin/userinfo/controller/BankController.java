/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Bank;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BankService;
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
import org.springframework.stereotype.Service;

/**
 *
 * @author viett
 */
@Service
@AllArgsConstructor
public class BankController extends BaseController implements BankService {

    public static final Logger log = Logger.getLogger(BankController.class.getName());

    @Override
    public ResponseEntity searchBankAdminList(SearchRequest request) {
        log.debug(request.toString());
        BaseResponse response = new BaseResponse();
        List banks = new ArrayList<>();
        String searchParam = request.getSearchParam();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            if (searchParam != null && !searchParam.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "BankCode");
                f.put("value", searchParam);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "BankName");
                f.put("value", searchParam);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "HisId");
            s.put("order", "ASC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        Bank bank = new Bank();
                        if (bank.parse((Map) o)) {
                            banks.add(bank);
                        }
                    }
                }
            }
            response.setStatus(ResponseConstants.SERVICE_SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
        } catch (Exception ex) {
            log.debug(ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
        }
        response.setData(banks);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity getByBankCode(SearchRequest request) {
        return null;
    }

    @Override
    public ResponseEntity ceateBankAdmin(Bank request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getBankCode() != null && !request.getBankCode().isEmpty()) {
                try {
                    update(AerospikeFactory.getInstance().onlyCreatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, request.getBankCode(), request.toBins());
                    response.setData(Arrays.asList(request));
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    response.setStatus(ResponseConstants.SERVICE_FAIL);
                    response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
                }

            } else {
                log.error("Data invalid");
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Data invalid");
            }

        } catch (AerospikeException ex) {
            log.error(ex.getMessage());
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity deleteBankAdmin(Bank request) {

        BaseResponse response = new BaseResponse();
        try {
            if (request.getBankCode() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, request.getBankCode());
                if (rec != null) {
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, request.getBankCode());
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_NOT_FOUND);
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
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
    public ResponseEntity updateBankAdmin(Bank request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getBankCode() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, request.getBankCode());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BANKADMIN_SET, request.getBankCode(), request.toBins());
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_NOT_FOUND);
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
