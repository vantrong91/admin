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
import com.jsoniter.output.JsonStream;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.pushnotify.Constant;
import com.vtgo.vn.admin.pushnotify.DataGoodsOwner;
import com.vtgo.vn.admin.pushnotify.MsgNotifyGoodsOwner;
import com.vtgo.vn.admin.pushnotify.MsgPushQueue;
import com.vtgo.vn.admin.pushnotify.NotificationObjectPushToGoodsOwner;
import com.vtgo.vn.admin.pushnotify.Publish;
import com.vtgo.vn.admin.pushnotify.TitleObj;
import com.vtgo.vn.admin.userinfo.BO.GoodOwner;
import com.vtgo.vn.admin.userinfo.BO.InsuranceOrder;
import com.vtgo.vn.admin.userinfo.request.InsuOrderCompleteRequest;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.InsuranceOrderService;
import com.vtgo.vn.admin.util.SequenceManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
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
                }  else{
                    response.setStatus(ResponseConstants.SERVICE_ERROR);
                    response.setMessage(ResponseConstants.SERVICE_INSURANCE_ORDER_NOT_FOUND);
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

    @Override
    public ResponseEntity getComplete(InsuranceOrder request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity completeInsuOrder(InsuOrderCompleteRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            if(request.getOrderId() !=null){
                String orderId = request.getOrderId();
                List<InsuranceOrder> lstInsuOrder = new ArrayList<>();
                Map<String, Object> argument = new HashMap<>();
                List<Value.MapValue> argumentFilter = new ArrayList<>();
                
                Map<String, Object> f = new HashMap<>();
                f.put("field", "OrderId");
                f.put("value", orderId);
                f.put("operator", "=");
                argumentFilter.add(new Value.MapValue(f));
                
                List<Value.MapValue> argumentSorter = new ArrayList<>();
                Map<String, Object> s1 = new HashMap<>();
                s1.put("sort_key", "OrderId");
                s1.put("order", "ASC");
                s1.put("type", "STRING");
                argumentSorter.add(new Value.MapValue(s1));
                
                argument.put("sorters", new Value.ListValue(argumentSorter));
                argument.put("filters", new Value.ListValue(argumentFilter));
                
                ResultSet rs = AerospikeFactory.getInstance().aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
                
                Iterator<Object> objectIterator = rs.iterator();
                while(objectIterator.hasNext()){
                    ArrayList arrList = (ArrayList) objectIterator.next();
                    arrList.forEach((o) -> {
                        InsuranceOrder myOrder = new InsuranceOrder();
                        if(myOrder.parse((Map) o)){
                            lstInsuOrder.add(myOrder);
                        }
                    });
                }
                
                if(lstInsuOrder.size() >0){
                    InsuranceOrder newOrder = new InsuranceOrder();
                    newOrder = lstInsuOrder.get(0);
                    newOrder.setState(2);
                    newOrder.setAccountId(request.getAccountId());
                    newOrder.setContractNo(request.getContractNo());
                    newOrder.setInsuranPrice(request.getInsuranPrice());
                    newOrder.setInsuranSpend(request.getInsuranSpend());
                    newOrder.setOrderId(orderId);
                    newOrder.setSumInsuPrice(request.getSumInsuPrice());
                    newOrder.setUpdateTime(request.getUpdateTime());
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.INSURANCE_ORDER_SET, request.getOrderId(), newOrder.toBins());
                    
                    notificationToGoodsOwner(request.getMessage(), request.getOrderId(), newOrder.getAccountId());
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                    response.setData(Arrays.asList(newOrder));
                    response.setStatus(ResponseConstants.SUCCESS);
                }else{
                    response.setMessage("No record");
                    response.setStatus(ResponseConstants.SERVICE_FAIL);
                }
                return ResponseEntity.status(HttpStatus.OK).body(response);
                
            }else{
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC + ".Cannot get OrderId");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    private void notificationToGoodsOwner(String mess, String orderId, Long accoutId) throws TimeoutException, IOException{
        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.OWNER);
        NotificationObjectPushToGoodsOwner objectPushToGoodsOwner = new NotificationObjectPushToGoodsOwner();
        objectPushToGoodsOwner.setAccountId(accoutId);
        objectPushToGoodsOwner.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);
        MsgNotifyGoodsOwner msgNotifyGoodsOwner = new MsgNotifyGoodsOwner();
        TitleObj titleObj = new TitleObj();
        titleObj.setTitle("VTGO");
        titleObj.setBody("Bạn nhận được thông báo mới");
        msgNotifyGoodsOwner.setNotification(titleObj);
        DataGoodsOwner dataGoodsOwner = new DataGoodsOwner();
        dataGoodsOwner.setType("9");
        dataGoodsOwner.setOrderId(orderId);
        dataGoodsOwner.setPromotionCode("PROMO");
        dataGoodsOwner.setMsg(mess + "\n" + "Mã ĐH: " + orderId);
        msgNotifyGoodsOwner.setData(dataGoodsOwner);
        objectPushToGoodsOwner.setMessage(msgNotifyGoodsOwner);
        msgPushQueue.setData(Arrays.asList(objectPushToGoodsOwner));
        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
    }

}
