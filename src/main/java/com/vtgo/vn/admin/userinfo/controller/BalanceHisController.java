/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.BalanceHis;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceHisService;
import com.vtgo.vn.admin.util.SequenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
public class BalanceHisController extends BaseController implements BalanceHisService {

    private static final Logger log = Logger.getLogger(BalanceHisController.class.getName());

    @Override
    public ResponseEntity searchBalanceHis(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<BalanceHis> listBalance = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchVal = request.getSearchParam();
            if (searchVal != null && !searchVal.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "HisId");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "Account");
                f.put("value", searchVal);
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
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        BalanceHis balanceHis = new BalanceHis();
                        if (balanceHis.parse((Map) o)) {
                            listBalance.add(balanceHis);
                        }
                    }
                }
            }
            response.setData(listBalance);
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
    public ResponseEntity getBalanceHisById(BalanceHis request
    ) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, request.getHisId());
            log.debug(request.getHisId());

            if (rec != null) {
                BalanceHis bal = new BalanceHis();
                bal.parse(rec);
                response.setData(Arrays.asList(bal));
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_GET_BALANCE_FAIL);
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
    public ResponseEntity getBalanceHisByAccId(BalanceHis request) {

        BaseResponse response = new BaseResponse();
        try {
            Long getId = request.getAccountId();
            if (getId != null) {
                RecordSet resultSet = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, "Account",
                        "AccountIdx", request.getAccountId());
                if (resultSet != null) {
                    Iterator<KeyRecord> objectIterator = resultSet.iterator();
                    List<BalanceHis> lstBalan = new ArrayList<>();
                    while (objectIterator.hasNext()) {
                        try {
                            BalanceHis myBal = new BalanceHis();
                            KeyRecord myKey = objectIterator.next();
                            if (myBal.parse(myKey.record)) {
                                lstBalan.add(myBal);
                            }
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                        }

                    }
                    response.setData(lstBalan);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                }
            } else {
                response.setMessage(ResponseConstants.SERVICE_GET_BALANCE_FAIL);
            }

            response.setStatus(ResponseConstants.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity createBalanceHis(BalanceHis request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getAccountId() != null && request.getBalanceBefor() != null && request.getBalanceAfter()
                    != null) {
                try {
                    long hisId = SequenceManager.getInstance().getSequence(BalanceHis.class.getSimpleName());
                    if (hisId < 0) {
                        response.setStatus(ResponseConstants.SERVICE_FAIL);
                        response.setMessage("Get hisId sequence error");
                    }
                    request.setHisId(hisId);
                    update(AerospikeFactory.getInstance().onlyCreatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, request.getHisId(), request.toBins());
//                    delete(AerospikeFactory.getInstance().writePolicy,
//                            DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, request.getHisId());
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

}
