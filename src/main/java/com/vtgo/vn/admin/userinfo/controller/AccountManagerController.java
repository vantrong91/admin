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
import com.jsoniter.output.JsonStream;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.AcccountStateConstant;
import com.vtgo.vn.admin.constant.AccountType;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.pushnotify.Constant;
import com.vtgo.vn.admin.pushnotify.EmailObject;
import com.vtgo.vn.admin.pushnotify.MsgPushQueue;
import com.vtgo.vn.admin.pushnotify.NotificationObject;
import com.vtgo.vn.admin.pushnotify.Publish;
import com.vtgo.vn.admin.userinfo.BO.AccountManager;
import com.vtgo.vn.admin.userinfo.BO.Account;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.AccountManagerService;
import com.vtgo.vn.admin.util.SecurityUtils;
import com.vtgo.vn.admin.util.SequenceManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender javaMailSender;

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
                f = new HashMap<>();
                f.put("field", "PhoneNumber");
                f.put("value", searchVal);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }
            if (request.getSearchParam2() != null) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "AccountType");
                f.put("value", request.getSearchParam2());
                f.put("operator", "=");
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
    public ResponseEntity searchByEmail(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchVal = request.getSearchParam();
            if (searchVal != null && !searchVal.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "Email");
                f.put("value", searchVal);
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
            log.debug(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity create(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
//            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE,
//                    DatabaseConstants.ACCOINT_MAN_SET, "Email", "EmailIdx", request.getEmail());
//            if (rs != null && rs.iterator().hasNext()) {
//                response.setStatus(ResponseConstants.SERVICE_FAIL);
//                response.setMessage("Email đã được sử dụng");
//                return ResponseEntity.status(HttpStatus.OK).body(response);
//            }
//            rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE,
//                    DatabaseConstants.ACCOINT_MAN_SET, "PhoneNumber", "PhoneNumberIdx", request.getPhoneNumber());
//            if (rs != null & rs.iterator().hasNext()) {
//                response.setStatus(ResponseConstants.SERVICE_FAIL);
//                response.setMessage("Số điện thoại đã được sử dụng");
//                return ResponseEntity.status(HttpStatus.OK).body(response);
//            }
            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE,
                    DatabaseConstants.ACCOINT_MAN_SET, "AccountCode", "AccountCodeIdx", getAccountCode(request.getAccountType(), request.getPhoneNumber()));
            if (rs != null && rs.iterator().hasNext()) {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage("SĐT đã được dùng cho loại tài khoản này");
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
            String token = SecurityUtils.createToken(String.valueOf(accountId), new Date());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            lstBin.add(new Bin("Password", bCryptPasswordEncoder.encode(password)));
            lstBin.add(new Bin("Salt", salt));
            lstBin.add(new Bin("Email", request.getEmail()));
            lstBin.add(new Bin("FullName", request.getFullName()));
            lstBin.add(new Bin("PhoneNumber", request.getPhoneNumber()));
            lstBin.add(new Bin("AccountType", request.getAccountType()));
            String accountCode = getAccountCode(request.getAccountType(), request.getPhoneNumber());
            lstBin.add(new Bin("AccountCode", accountCode));
            lstBin.add(new Bin("AccountToken", token));
            lstBin.add(new Bin("FileAvata", request.getFileAvata()));
            lstBin.add(new Bin("State", request.getState()));
            lstBin.add(new Bin("BankCodeTran", request.getBankCodeTran()));
            try {
                update(AerospikeFactory.getInstance().onlyCreatePolicy, DatabaseConstants.NAMESPACE,
                        DatabaseConstants.ACCOINT_MAN_SET, accountId, lstBin.toArray(new Bin[lstBin.size()]));
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
            log.error(e, e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    public String getAccountCode(Long type, String phoneNum) {
        int types = Integer.parseInt(String.valueOf(type));
        switch (types) {
            case AccountType.BUSINESS:
                return "KD" + phoneNum;
            case AccountType.SUPPORT:
                return "HT" + phoneNum;
            case AccountType.ACCOUNTANT:
                return "KT" + phoneNum;
            case AccountType.TECHNICAL:
                return "KTH" + phoneNum;
            case AccountType.MANAGE:
                return "QL" + phoneNum;
            case AccountType.INSURANCE:
                return "BH" + phoneNum;
            default:
                return "US" + phoneNum;

        }
    }

    @Override
    public ResponseEntity checkLogin(AccountManager request) {
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        String password = "";
        Long accountType = null;
        Long accountId = null;
        try {
            RecordSet rs = AerospikeFactory.getInstance().queryByIndex(DatabaseConstants.NAMESPACE,
                    DatabaseConstants.ACCOINT_MAN_SET, "AccountCode", "AccountCodeIdx", request.getAccountCode());
            if (rs != null && rs.iterator().hasNext()) {
                try {
                    log.info(request.toString());
                    Map<String, Object> argument = new HashMap<>();
                    List<Value.MapValue> argumentFilter = new ArrayList<>();
                    String searchVal = request.getAccountCode();
                    if (searchVal != null && !searchVal.isEmpty()) {
                        Map<String, Object> f = new HashMap<>();
                        f.put("field", "AccountCode");
                        f.put("value", searchVal);
                        f.put("operator", "=");
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
                            .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE,
                                    DatabaseConstants.ACCOINT_MAN_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
                    if (resultSet != null) {
                        Iterator<Object> objectIterator = resultSet.iterator();
                        while (objectIterator.hasNext()) {
                            ArrayList arrayList = (ArrayList) objectIterator.next();
                            for (Object o : arrayList) {
                                AccountManager accountManager = new AccountManager();
                                if (accountManager.parse((Map) o)) {
                                    password = accountManager.getPassword();
                                    accountType = accountManager.getAccountType();
                                    accountId = accountManager.getAccountId();
                                    //Check pass
                                    BCryptPasswordEncoder bCryptPasswordEncode = new BCryptPasswordEncoder();
                                    if (!bCryptPasswordEncode.matches(request.getPassword(), password)) {
                                        response.setStatus(DatabaseConstants.ResultCode.FAIL);
                                        response.setMessage("Wrong password!");
                                    } //Check Account type
                                    else if (accountType == 0 || accountType == 5 || accountType == 6 || accountType == 7 || accountType == 8 || accountType == 9 || accountType == 10) {
                                        String token = SecurityUtils.createToken(String.valueOf(accountId), new Date());
                                        accountManager.setAccountToken(token);
                                        update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                                                DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, accountId, accountManager.toBins());
                                        listAcc.add(accountManager);
                                        response.setData(listAcc);
                                        response.setStatus(ResponseConstants.SUCCESS);
                                        response.setMessage("OK");
                                    } else {
                                        response.setStatus(ResponseConstants.SERVICE_LOGIN_FAIL);
                                        response.setMessage("Your account doesn't have access");
                                    }

                                }
                            }
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } catch (Exception ex) {
                    log.debug(ex.getMessage(), ex);
                    response.setStatus(ResponseConstants.SERVICE_FAIL);
                    response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

            }
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_ACCOUNT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e, e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity delete(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
            if (rec != null) {
                delete(AerospikeFactory.getInstance().writePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setStatus(ResponseConstants.SERVICE_ERROR);
                response.setMessage(ResponseConstants.SERVICE_ACCOUNT_NOT_FOUND);
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity logout(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                AccountManager accountManager = new AccountManager();
                accountManager.parse(rec);
                accountManager.setAccountToken("");
                update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId(), accountManager.toBins());
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity update(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
            if (rec != null) {
                String password = request.getPassword();
                String salt = SecurityUtils.createSalt();;
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                request.setPassword(bCryptPasswordEncoder.encode(password));
                request.setSalt(salt);
                update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                        DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId(), request.toBins());
                response.setStatus(ResponseConstants.SUCCESS);
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setStatus(ResponseConstants.SERVICE_ERROR);
                response.setMessage(ResponseConstants.SERVICE_ACCOUNT_NOT_FOUND);
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
    public ResponseEntity updateInfo(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            List<Bin> lstBin = new ArrayList();
            lstBin.add(new Bin("FileAvata", request.getFileAvata()));
            lstBin.add(new Bin("FullName", request.getFullName()));
            lstBin.add(new Bin("Email", request.getEmail()));
            lstBin.add(new Bin("PhoneNumber", request.getPhoneNumber()));
            update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                    DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId(), lstBin.toArray(new Bin[lstBin.size()]));
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getByAccountCode(AccountManager request) {
        BaseResponse response = new BaseResponse();
        List<AccountManager> listAcc = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String accCode = request.getAccountCode();
            if (accCode != null && !accCode.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "AccountCode");
                f.put("value", accCode);
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
            log.debug(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity changeState(AccountManager request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId());
            if (rec != null) {
                if (request.getState() == AcccountStateConstant.ACTIVE
                        || request.getState() == AcccountStateConstant.BLOCK
                        || request.getState() == AcccountStateConstant.CREATE
                        || request.getState() == AcccountStateConstant.TERMINATE) {
                    List<Bin> lstBin = new ArrayList();
                    lstBin.add(new Bin("State", request.getState()));
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ACCOINT_MAN_SET, request.getAccountId(), lstBin.toArray(new Bin[lstBin.size()]));

                    //if state from CREATE change to ACTIVE => send email to user
                    //get current state
                    AccountManager accountManager = new AccountManager();
                    accountManager.parse(rec);
                    if (accountManager.getEmail() != null) {
                        if (accountManager.getState() == 0L && request.getState() == 1L) {
                            sendEmailToUser(request.getEmail());
                        }
                    }

                    response.setStatus(ResponseConstants.SUCCESS);
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                    response.setData(Arrays.asList(request));
                } else {
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage("State invalid");
                }
            } else {
                response.setStatus(ResponseConstants.SERVICE_CUSTOMER_NOT_FOUND);
                response.setMessage(ResponseConstants.SERVICE_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void sendEmailToUser(String email) {
        try {
            MsgPushQueue msgPushQueue = new MsgPushQueue();
            NotificationObject<EmailObject> noti = new NotificationObject<>();
            noti.setNotifyType(Constant.NOTIFY_TYPE.EMAIL);
            EmailObject emailObj = new EmailObject();
            emailObj.setSubject("[VTGO] Thông báo kích hoạt thành công tài khoản");
            emailObj.setToEmail(email);
            StringBuilder builder = new StringBuilder();
            builder.append("Xin chào! \nTài khoản của bạn đã được kích hoạt. Tên đăng nhập và mật khẩu đã được gửi lúc đăng ký tài khoản.");
            emailObj.setContent(builder.toString());
            noti.setData(Arrays.asList(emailObj));
            msgPushQueue.setNotificationObect(noti);
            msgPushQueue.setTypeSend(Constant.NOTIFY_TYPE.EMAIL);

            String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
            log.info("Mail to " + email + " -> sent!");
        } catch (Exception ex) {
            log.error("Cannot send email: " + ex.toString());
        }
    }
}
