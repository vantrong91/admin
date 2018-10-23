/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;


import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.listener.ExecuteListener;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.aerospike.DatabaseMsg;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.BalanceTemp;
import com.vtgo.vn.admin.userinfo.BO.Transaction;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceService;
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
 * @author Admin
 */
@Service
@AllArgsConstructor
public class BalanceController extends BaseController implements BalanceService{
    private static final Logger logger = Logger.getLogger(BalanceController.class.getName());

    @Override
    public ResponseEntity searchBalance(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<BalanceTemp> balance = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();

            String searchValue = request.getSearchParam();
            if (searchValue != null && !searchValue.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "accountId");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));                
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "accountId");
            s1.put("order", "DESC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
//                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
                                        .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, "bankAccount", "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));

            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        BalanceTemp temp = new BalanceTemp();
                        if (temp.parse((Map) o)) {
                            balance.add(temp);
                        }
                    }
                }
            }
            response.setData(balance);
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getBalanceId(BalanceTemp request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, request.getAccountId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                BalanceTemp balance = new BalanceTemp();
                balance.parse(rec);
                response.setData(Arrays.asList(balance));
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
    
    @Override
    public ResponseEntity transaction(Transaction request) {
        BaseResponse response = new BaseResponse();
        long accountId = request.getAccountId();
        List<Value> lstParam = new ArrayList<Value>();
        lstParam.add(Value.get(request.getBalType()));
        lstParam.add(Value.get(request.getChange()));

        AerospikeFactory.getInstance().execute(new ExecuteListener() {
            @Override
            public void onSuccess(Key key, Object ret) {
                if (ret == null) {
                    logger.info("Query for key " + key.userKey.toString() + " not found");
                }
                logger.info("In query aerospike success for key:" + key.userKey.toString());
                Map mapRet = (Map) ret;
                Integer resultCode = Integer.parseInt((String) mapRet.get("ResultCode"));
                String resultText = (String) mapRet.get("ResultText");
                response.setStatus(resultCode);
                response.setMessage(resultText);
            }

            @Override
            public void onFailure(AerospikeException ae) {
                try {
                    logger.info("In query aerospike fail:");
                    ae.getResultCode();
                    ae.getMessage();
                } catch (Exception ex) {
                    logger.error(ex, ex);
                }
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            }
        }, AerospikeFactory.getInstance().writePolicy,
                DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, accountId, "balance-utils-admin", "updateBalance",
                lstParam.toArray(new Value[lstParam.size()]));

        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
