/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.TransferFee;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.TransferFeeService;
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
import org.springframework.stereotype.Service;

/**
 *
 * @author viett
 */
@Service
@AllArgsConstructor
public class TransferFeeController extends BaseController implements TransferFeeService {

    public static Logger log = Logger.getLogger(TransferFeeController.class.getName());

    @Override
    public ResponseEntity search(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<TransferFee> lstBank = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchVal = request.getSearchParam();
            log.info(searchVal);
            if (searchVal != null && !searchVal.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "TransferId");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "BankName");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "BankCode");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "TransferId");
            s.put("order", "DESC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        TransferFee transferFee = new TransferFee();
                        if (transferFee.parse((Map) o)) {
                            lstBank.add(transferFee);
                        }
                    }
                }
            }

            response.setData(lstBank);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            response.setStatus(ResponseConstants.SERVICE_SUCCESS);
        } catch (AerospikeException ex) {
            log.debug(ex.getMessage(), ex);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity getConnect(TransferFee request) {
        BaseResponse response = new BaseResponse();
        List<TransferFee> lstBank = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            Long searchVal = request.getConnect();
            if (searchVal != null) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "Connect");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "Fee");
            s.put("order", "ASC");
            s.put("type", "NUMBER");
            argumentSorters.add(new Value.MapValue(s));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        TransferFee transferFee = new TransferFee();
                        if (transferFee.parse((Map) o)) {
                            lstBank.add(transferFee);
                        }
                    }
                }
            }

            response.setData(lstBank);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            response.setStatus(ResponseConstants.SERVICE_SUCCESS);
        } catch (AerospikeException ex) {
            log.debug(ex.getMessage(), ex);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity update(TransferFee request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getTransferId()!= null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, request.getTransferId());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, request.getTransferId(), request.toBins());
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_VEHICLE_OWNER_NOT_FOUND);
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
    public ResponseEntity delete(TransferFee request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getTransferId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, request.getTransferId());
                if (rec != null) {
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, request.getTransferId());

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
    public ResponseEntity create(TransferFee request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getBankName() != null) {
//                RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, "BankCode",
//                        "BankCodeIdx", request.getTransferId());
//                if (rs != null && rs.iterator().hasNext()) {
//                    response.setStatus(ResponseConstants.SERVICE_FAIL);
//                    response.setMessage("BankName was existed");
//                    return ResponseEntity.status(HttpStatus.OK).body(response);
//                }
                long transferId = SequenceManager.getInstance().getSequence(TransferFeeController.class.getSimpleName());
                if (transferId <= 0) {
                    response.setStatus(ResponseConstants.SERVICE_FAIL);
                    response.setMessage("Get transferId sequence error");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
                request.setTransferId(transferId);
                update(AerospikeFactory.getInstance().onlyCreatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.TRANSFER_FEE_SET, request.getTransferId(), request.toBins());
                response.setData(Arrays.asList(request));
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
