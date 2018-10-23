/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.vtgo.vn.admin.base.BaseController;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.listener.ExecuteListener;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.AccountType;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Account;
import com.vtgo.vn.admin.userinfo.BO.BankAccount;
import com.vtgo.vn.admin.userinfo.BO.VehicleOwner;
import com.vtgo.vn.admin.userinfo.request.VehicleOwnerSearchRequest;
import com.vtgo.vn.admin.userinfo.service.VehicleOwnerService;
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
 * @author HP
 */
@Service
@AllArgsConstructor
public class VehicleOwnerController extends BaseController implements VehicleOwnerService {

    private static final Logger logger = Logger.getLogger(VehicleOwnerController.class.getName());

    @Override
    public ResponseEntity searchVehicleOwner(VehicleOwnerSearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<VehicleOwner> lstVehicleOwners = new ArrayList<>();
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
                f.put("field", "ContactPhone");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "BusiLic");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "Mod");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "Taxcode");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
                f = new HashMap<>();
                f.put("field", "LicenseNo");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            if (request.getOwnerType() != null && request.getOwnerType() >= 0) {
                Map<String, Object> f1 = new HashMap<>();
                f1.put("field", "OwnerType");
                f1.put("value", request.getOwnerType());
                f1.put("operator", "=");
                argumentFilter.add(new Value.MapValue(f1));
            }
            if (request.getAccountId() != null) {
                Map<String, Object> f4 = new HashMap<>();
                f4.put("field", "AccountId");
                f4.put("value", request.getAccountId());
                f4.put("operator", "=");
                argumentFilter.add(new Value.MapValue(f4));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "FullName");
            s1.put("order", "ASC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, "vgo", "vehicleOwner", "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> objectIterator = resultSet.iterator();
                while (objectIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objectIterator.next();
                    for (Object o : arrayList) {
                        VehicleOwner vehicleOwner = new VehicleOwner();
                        if (vehicleOwner.parse((Map) o)) {
                            lstVehicleOwners.add(vehicleOwner);
                        }
                    }
                }
            }
            response.setData(lstVehicleOwners);
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
    public ResponseEntity getVehicleOwnerById(VehicleOwnerSearchRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId());
            Record recBank = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.BANK_SET, request.getAccountId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                VehicleOwner vehicleOwner = new VehicleOwner();
                vehicleOwner.parse(rec);
                List<BankAccount> bankAccounts = new ArrayList<>();
                if (recBank != null) {
                    Map<Long, Map<String, String>> mapBank = (Map<Long, Map<String, String>>) recBank.getMap("BankInfo");
                    if (mapBank != null && !mapBank.isEmpty()) {
                        mapBank.keySet().forEach((key) -> {
                            BankAccount bankAccount = new BankAccount();
                            Map<String, String> mapValue = mapBank.get(key);
                            if (mapValue != null && !mapValue.isEmpty()) {
                                bankAccount.setBankCode(mapValue.get("BankCode"));
                                bankAccount.setAccountNumber(mapValue.get("AccountNumber"));
                                bankAccount.setOwnerName(mapValue.get("OwnerName"));
                                bankAccount.setBranch(mapValue.get("Branch"));
                                bankAccounts.add(bankAccount);
                            }
                        });
                    }
                }
                vehicleOwner.setBankAccountLst(bankAccounts);
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
    public ResponseEntity update(VehicleOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getAccountId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId().toString(), request.toBins());
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
    public ResponseEntity create(VehicleOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, "Email",
                    "EmailIdx", request.getEmail());
            if (rs != null && rs.iterator().hasNext()) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("Email was existed");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, "PhoneNumber",
                    "PhoneNumberIdx", request.getContactPhone());
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
            lstBin.add(new Bin("Email", request.getEmail()));
            lstBin.add(new Bin("FullName", request.getFullName()));
            lstBin.add(new Bin("PhoneNumber", request.getContactPhone()));
            lstBin.add(new Bin("AccountType", AccountType.VEHICLE_OWNER));
            String accountCode = "US" + request.getContactPhone();
            lstBin.add(new Bin("AccountCode", accountCode));

            // Create Balance
            List<Value> balParam = new ArrayList<Value>();
            balParam.add(Value.get(1));//BalType
            balParam.add(Value.get(0));//Gross
            balParam.add(Value.get(0));//Consume
            balParam.add(Value.get(1861722000000f));//ExpDate
            balParam.add(Value.get(0));//Reserve
            balParam.add(Value.get("VH" + request.getContactPhone()));//AcctNumber
            balParam.add(Value.get(accountId));
            try {
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

                update(AerospikeFactory.getInstance().onlyCreatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, accountId, lstBin.toArray(new Bin[lstBin.size()]));
            } catch (AerospikeException e) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            request.setAccountId(accountId);
            try {
                update(AerospikeFactory.getInstance().onlyCreatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId(), request.toBins());
            } catch (AerospikeException e) {
                //Rollback table account             
                try {
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, accountId);
                } catch (AerospikeException e1) {
                    logger.error("Rollback account " + accountId + " fail " + e1.getMessage() + " " + e1.getResultCode());
                }
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            if (request.getBankAccountLst() != null && !request.getBankAccountLst().isEmpty()) {
                List<BankAccount> lstBankCard = request.getBankAccountLst();
                List<Bin> bins = new ArrayList<>();
                bins.add(new Bin("AccountId", request.getAccountId()));
                Map<Integer, Map<String, String>> mapBank = new HashMap<>();
                int j = 1;
                for (BankAccount bankCard : lstBankCard) {
                    Map<String, String> infoBank = new HashMap<>();
                    infoBank.put("BankCode", bankCard.getBankCode());
                    infoBank.put("AccountNumber", bankCard.getAccountNumber());
                    infoBank.put("OwnerName", bankCard.getOwnerName());
                    infoBank.put("Branch", bankCard.getBranch());
                    mapBank.put(j, infoBank);
                    j++;
                }
                if (!mapBank.isEmpty()) {
                    bins.add(new Bin("BankInfo", mapBank));
                    AerospikeFactory.getInstance().update(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BANK_SET, request.getAccountId(), bins.toArray(new Bin[bins.size()]));
                }
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
    public ResponseEntity delete(VehicleOwner request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getAccountId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId());
                if (rec != null) {
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.VEHICLE_OWNER_SET, request.getAccountId());
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOUNT_SET, request.getAccountId());
                    delete(AerospikeFactory.getInstance().writePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE, request.getAccountId());
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
