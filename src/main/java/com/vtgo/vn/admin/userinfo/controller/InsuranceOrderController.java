/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.Record;
import com.aerospike.client.Value;

import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.GoodOwner;
import com.vtgo.vn.admin.userinfo.BO.InsuranceOrder;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.InsuranceOrderService;
import com.vtgo.vn.admin.util.SequenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author tvhdh
 */
@Service
@AllArgsConstructor
public class InsuranceOrderController extends BaseController implements InsuranceOrderService {

    private static final Logger logger = Logger.getLogger(InsuranceOrderController.class.getName());

    @Override
    public ResponseEntity create(InsuranceOrder request) {
        BaseResponse response = new BaseResponse();
        try {
//            if (request.getContractNo() != null) {
//                RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, "ContractNo", "ContractNoIdx", request.getContractNo());
//                if (rs != null && rs.iterator().hasNext()) {
//                    response.setStatus(ResponseConstants.SERVICE_FAIL);
//                    response.setMessage("ContractNo was existed");
//                    return ResponseEntity.status(HttpStatus.OK).body(response);
//                }
            long accountId = SequenceManager.getInstance().getSequence(InsuranceOrder.class.getSimpleName());
            if (accountId <= 0) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Get account Id sequence error");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            request.setState(1);
            update(AerospikeFactory.getInstance().onlyCreatePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId(), request.toBins());

            response.setData(Arrays.asList(request));
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
//            } else {
//                response.setStatus(ResponseConstants.SERVICE_FAIL);
//                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
//                return ResponseEntity.status(HttpStatus.OK).body(response);
//            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity update(InsuranceOrder request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getOrderId()!= null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId());
                if(rec != null){
                    request.setState(1);
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy, DatabaseConstants.NAMESPACE, 
                            DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId(), request.toBins());
                    
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                    InsuranceOrder insuOrder = new InsuranceOrder();
                    insuOrder.parse(rec);
                    response.setData(Arrays.asList(insuOrder));
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
    public ResponseEntity delete(InsuranceOrder request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getOrderId()!= null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId());
                if (rec != null) {
                    delete(AerospikeFactory.getInstance().writePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId());
                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_INSURANCE_ORDER_NOT_FOUND);
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);;
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
    public ResponseEntity getById(InsuranceOrder request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, 
                    request.getOrderId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                InsuranceOrder insuOrder = new InsuranceOrder();
                insuOrder.parse(rec);
                response.setData(Arrays.asList(insuOrder));
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
    public ResponseEntity searchInsuOrder(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<InsuranceOrder> lstInsuOrder = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();

            String searchValue = request.getSearchParam();
            if (searchValue != null && !searchValue.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "ContractNo");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "ContractNo");
            s1.put("order", "ASC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance().aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        InsuranceOrder insuOrder = new InsuranceOrder();
                        if (insuOrder.parse((Map) o)) {
                            lstInsuOrder.add(insuOrder);
                        }
                    }
                }
            }
            response.setData(lstInsuOrder);
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
    public ResponseEntity getGoodsOwnerById(InsuranceOrder request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE,
                    DatabaseConstants.GOOD_OWNER,
                    request.getAccountId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                GoodOwner goodOwner = new GoodOwner();
                goodOwner.parse(rec);
                response.setData(Arrays.asList(goodOwner));
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
