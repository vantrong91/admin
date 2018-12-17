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
import com.vtgo.vn.admin.constant.AccountType;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.pushnotify.Constant;
import com.vtgo.vn.admin.pushnotify.SendNotify;
import com.vtgo.vn.admin.userinfo.BO.AccountManager;
import com.vtgo.vn.admin.userinfo.BO.BalanceTemp;
import com.vtgo.vn.admin.userinfo.BO.Transaction;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
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
public class BalanceController extends BaseController implements BalanceService {

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
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));

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

                if (resultCode != 4) {// resultCode == 4 <=> ["ResultText"]="Balance not enough"
                    int accountType = getAccountType(request.getAccountId());
                    try {
                        Long moneyChange = request.getChange();
                        String strMoney = "";
                        if (moneyChange > 0) {
                            strMoney = "+" + String.valueOf(moneyChange);
                        } else {
                            strMoney = String.valueOf(moneyChange);
                        }
                        String content = "[Thông báo thay đổi số dư]\nTài khoản " + accountId + ": " + strMoney + "đ.\nNội dung: " + request.getContent();
                        switch (accountType) {
                            case AccountType.DRIVER:
                                logger.info(content);
                                if (moneyChange < 0) {
                                    SendNotify.sendToDriver(accountId, Constant.DRIVER_NOTIFY_TYPE.ACCOUNT_WITHDRAWAL, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                if (moneyChange > 0) {
                                    SendNotify.sendToDriver(accountId, Constant.DRIVER_NOTIFY_TYPE.ACCOUNT_RECHARGE, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                break;
                            case AccountType.GOOD_OWNER:
                                logger.info(content);
                                if (moneyChange < 0) {
                                    SendNotify.sendToGoodOwner(accountId, Constant.OWNER_NOTIFY_TYPE.ACCOUNT_WITHDRAWAL, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                if (moneyChange > 0) {
                                    SendNotify.sendToGoodOwner(accountId, Constant.OWNER_NOTIFY_TYPE.ACCOUNT_RECHARGE, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                break;
                            case AccountType.VEHICLE_OWNER:
                                logger.info(content);
                                if (moneyChange < 0) {
                                    SendNotify.sendToVehicleOwner(accountId, Constant.VEHICLE_OWNER_NOTIFY_TYPE.ACCOUNT_WITHDRAWAL, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                if (moneyChange > 0) {
                                    SendNotify.sendToVehicleOwner(accountId, Constant.VEHICLE_OWNER_NOTIFY_TYPE.ACCOUNT_RECHARGE, "VTGO", "Bạn nhận được thông báo mới", content);
                                }
                                break;
                            default:
                                logger.debug("Account not in (1,2,3) => Cannot push notify to AccountID= " + accountId + "_AccountType = " + accountType);
                        }
                    } catch (TimeoutException ex) {
                        java.util.logging.Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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

    @Override
    public ResponseEntity searchAccountBalance() {

        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();

            Map<String, Object> f = new HashMap<>();

            for (int i = 1; i <= 3; i++) {
                f.put("field", "AccountType");
                f.put("value", i);
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
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            logger.debug(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    private int getAccountType(Long accountId) {
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, accountId);
            if (rec != null) {
                AccountManager accountManager = new AccountManager();
                accountManager.parse(rec);
                return Integer.parseInt(String.valueOf(accountManager.getAccountType()));
            } else {
                logger.debug("Cannot find accountId");
                return -1;
            }
        } catch (Exception ex) {
            logger.error(ex);
            return -1;
        }
    }
}
