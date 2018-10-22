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
import com.vtgo.vn.admin.constant.AccountType;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Account;
import com.vtgo.vn.admin.userinfo.BO.Driver;
import com.vtgo.vn.admin.userinfo.BO.GoodOwner;
import com.vtgo.vn.admin.userinfo.BO.VehicleOwner;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.request.VehicleOwnerSearchRequest;
import com.vtgo.vn.admin.userinfo.service.GoodOwnerService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@Service
@AllArgsConstructor
public class GoodOwnerController extends BaseController implements GoodOwnerService {

    private static final Logger logger = Logger.getLogger(GoodOwnerController.class.getName());

    @Override
    public ResponseEntity searchGoodOwner(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<GoodOwner> goodOwners = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();

            String searchValue = request.getSearchParam();
            if (searchValue != null && !searchValue.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "FullName");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "PhoneNumber");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "quotationID");
            s1.put("order", "ASC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        GoodOwner owner = new GoodOwner();
                        if (owner.parse((Map) o)) {
                            goodOwners.add(owner);
                        }
                    }
                }
            }
            response.setData(goodOwners);
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
    public ResponseEntity getGoodOwnerById(GoodOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getUserId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                VehicleOwner vehicleOwner = new VehicleOwner();
                vehicleOwner.parse(rec);
                response.setData(Arrays.asList(vehicleOwner));
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
    public ResponseEntity update(GoodOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getAccountId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getAccountId());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getAccountId(), request.toBins());
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
            logger.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity create(GoodOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, "PhoneNumber",
                    "PhoneNumberIdx", request.getPhoneNumber());
            if (rs != null && rs.iterator().hasNext()) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("PhoneNumber was existed");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            long accountId = SequenceManager.getInstance().getSequence(Account.class.getSimpleName());
            if (accountId <= 0) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Get accountId sequence error");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            //TODO create account
            List<Bin> lstBin = new ArrayList();
            lstBin.add(new Bin("AccountId", accountId));
            String password = SecurityUtils.genRandomPassword();
            String salt = SecurityUtils.createSalt();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            lstBin.add(new Bin("Password", bCryptPasswordEncoder.encode(password)));
            lstBin.add(new Bin("Salt", salt));
            lstBin.add(new Bin("PhoneNumber", request.getPhoneNumber()));
            lstBin.add(new Bin("AccountType", AccountType.GOOD_OWNER));
            lstBin.add(new Bin("FullName", request.getFullName()));
            String accountCode = "US" + request.getPhoneNumber();
            lstBin.add(new Bin("AccountCode", accountCode));
            
            // init Balance
            List<Value> balParam = new ArrayList<Value>();
            balParam.add(Value.get(1));//BalType
            balParam.add(Value.get(0));//Gross
            balParam.add(Value.get(0));//Consume
            balParam.add(Value.get(1861722000000f));//ExpDate
            balParam.add(Value.get(0));//Reserve
            balParam.add(Value.get("VP1" + request.getPhoneNumber()));//AcctNumber
            balParam.add(Value.get(accountId));
            try {
                //add Balance
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
                        logger.debug(resultCode);
                        logger.debug(resultText);
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
                        logger.debug(ResponseConstants.SERVICE_FAIL);
                        logger.debug(ResponseConstants.SERVICE_FAIL_DESC);
                    }
                },
                        AerospikeFactory.getInstance().writePolicy,
                        DatabaseConstants.NAMESPACE,
                        DatabaseConstants.BALANCE,
                        accountId,
                        "balance-utils-admin",
                        "createBalance",
                        balParam.toArray(new Value[balParam.size()]));
                //Add account
                update(AerospikeFactory.getInstance().onlyCreatePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, accountId, lstBin.toArray(new Bin[lstBin.size()]));
            } catch (AerospikeException e) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            request.setAccountId(accountId);
            try {
                update(AerospikeFactory.getInstance().onlyCreatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getAccountId(), request.toBins());
            } catch (AerospikeException e) {
                //Rollback table account
                try {
                    delete(AerospikeFactory.getInstance().writePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, accountId);
                } catch (AerospikeException e1) {
                    logger.error("Rollback account " + accountId + " fail " + e1.getMessage() + " " + e1.getResultCode());
                }
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response.setData(Arrays.asList(request));
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
    public ResponseEntity delete(GoodOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getAccountId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getAccountId());
                if (rec != null) {
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.GOOD_OWNER, request.getAccountId());
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, request.getAccountId());
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, request.getAccountId());
                    response.setStatus(ResponseConstants.SUCCESS);
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
            logger.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
