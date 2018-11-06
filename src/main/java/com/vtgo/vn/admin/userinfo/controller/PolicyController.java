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
import com.vtgo.vn.admin.userinfo.BO.Policy;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vtgo.vn.admin.userinfo.service.PolicyService;
import com.vtgo.vn.admin.util.SequenceManager;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author tvhdh
 */
@Service
@AllArgsConstructor

public class PolicyController extends BaseController implements PolicyService {

    private static final Logger logger = Logger.getLogger(PolicyController.class.getName());

    @Override
    public ResponseEntity search(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<Policy> lstPolicy = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchValue = request.getSearchParam();
            if (searchValue != null && !searchValue.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "PolicyId");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance().aggregate(
                    AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        Policy policy = new Policy();
                        if (policy.parse((Map) o)) {
                            lstPolicy.add(policy);
                        }
                    }
                }
            }
            response.setData(lstPolicy);
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getById(Policy request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET, request.getPolicyId());

            if (rec != null) {
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                Policy policy = new Policy();
                policy.parse(rec);
                response.setData(Arrays.asList(policy));
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_POLICY_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity update(Policy request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getPolicyId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET,
                        request.getPolicyId());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET,
                            request.getPolicyId(), request.toBins());
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_POLICY_NOT_FOUND);
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity create(Policy request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getPolicyId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET, request.getPolicyId());

                if (rec != null) {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage("ID đã được sử dụng");
                } else {
                    List<Bin> lstBin = new ArrayList();
                    lstBin.add(new Bin("PolicyId", request.getPolicyId()));
                    lstBin.add(new Bin("Constant", request.getConstant()));
                    lstBin.add(new Bin("RatioVat", request.getRatioVat()));
                    lstBin.add(new Bin("RatiRoseNoVat", request.getRatiRoseNoVat()));
                    lstBin.add(new Bin("RatioRoseVat", request.getRatioRoseVat()));
                    lstBin.add(new Bin("RatioVatTax", request.getRatioVatTax()));
                    lstBin.add(new Bin("RatioPerTax", request.getRatioPerTax()));
                    lstBin.add(new Bin("Description", request.getDescription()));
                    update(AerospikeFactory.getInstance().onlyCreatePolicy, DatabaseConstants.NAMESPACE,
                            DatabaseConstants.POLICY_SET, request.getPolicyId(), lstBin.toArray(new Bin[lstBin.size()]));
                    response.setData(Arrays.asList(request));
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error(e, e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity delete(Policy request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET, request.getPolicyId());
            if (rec != null) {
                delete(AerospikeFactory.getInstance().writePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.POLICY_SET, request.getPolicyId());
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setStatus(ResponseConstants.SERVICE_ERROR);
                response.setMessage(ResponseConstants.SERVICE_POLICY_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
