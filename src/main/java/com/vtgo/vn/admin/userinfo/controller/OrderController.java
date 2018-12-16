/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.jsoniter.output.JsonStream;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.pushnotify.Constant;
import com.vtgo.vn.admin.pushnotify.DataDriver;
import com.vtgo.vn.admin.pushnotify.MsgNotifyDriver;
import com.vtgo.vn.admin.pushnotify.MsgPushQueue;
import com.vtgo.vn.admin.pushnotify.NotificationObjectPushToDriver;
import com.vtgo.vn.admin.pushnotify.Publish;
import com.vtgo.vn.admin.pushnotify.TitleObj;
import com.vtgo.vn.admin.userinfo.BO.Order;
import com.vtgo.vn.admin.userinfo.request.OrderCompleteRequest;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.OrderService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import org.apache.log4j.PropertyConfigurator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author viett
 */
@Service
@AllArgsConstructor

public class OrderController extends BaseController implements OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @Override
    public ResponseEntity searchOrder(SearchRequest request) {
        LOGGER.debug("Controller / request: " + request.getSearchParam());
        BaseResponse response = new BaseResponse();
        List<Order> listOrder = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchValue = request.getSearchParam();

            if (searchValue != null && !searchValue.isEmpty()) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "OrderId");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "AccountId");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));

                f = new HashMap<>();
                f.put("field", "AccIdDriver");
                f.put("value", searchValue);
                f.put("operator", "contain");
                argumentFilter.add(new Value.MapValue(f));
            }

            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "OrderId");
            s1.put("order", "ASC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));

            if (resultSet != null) {
                Iterator<Object> objecIterator = resultSet.iterator();
                while (objecIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objecIterator.next();
                    arrayList.forEach((o) -> {
                        Order myOrder = new Order();
                        if (myOrder.parse((Map) o)) {
                            listOrder.add(myOrder);
                        }
                    });
                }
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setMessage("No record");
            }
            response.setData(listOrder);
            response.setStatus(ResponseConstants.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AerospikeException ex) {
            LOGGER.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getOrderById(Order request) {
        return null;
    }

    @Override
    public ResponseEntity create(Order request
    ) {
        return null;
    }

    @Override
    public ResponseEntity update(Order request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getOrderId() != null) {
                Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, request.getOrderId());
                if (rec != null) {
                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, request.getOrderId(), request.toBins());
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
            LOGGER.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity delete(Order request) {
        return null;

    }

    @Override
    public ResponseEntity getComplete(Order request) {
        BaseResponse response = new BaseResponse();
        List<Order> listOrder = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            Integer state = Integer.parseInt(String.valueOf(request.getState()));

            if (state != null) {
                Map<String, Object> f = new HashMap<>();
                switch (state) {
                    case 1:
                        f.put("field", "State");
                        f.put("value", 8L);
                        f.put("operator", "=");
                        argumentFilter.add(new Value.MapValue(f));
                        break;
//                    case 2:
//                        f.put("field", "State");
//                        f.put("value", 8L);
//                        f.put("operator", "!=");
//                        argumentFilter.add(new Value.MapValue(f));
//                        break;
                    default:
                        f.put("field", "State");
                        f.put("value", "");
                        f.put("operator", "contain");
                        argumentFilter.add(new Value.MapValue(f));
                        break;
                }

            }

            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "OrderId");
            s1.put("order", "ASC");
            s1.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));

            if (resultSet != null) {
                Iterator<Object> objecIterator = resultSet.iterator();
                while (objecIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objecIterator.next();
                    arrayList.forEach((o) -> {
                        Order myOrder = new Order();
                        if (myOrder.parse((Map) o)) {
                            listOrder.add(myOrder);
                        }
                    });
                }
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            } else {
                response.setMessage("No record");
            }
            response.setData(listOrder);
            response.setStatus(ResponseConstants.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AerospikeException ex) {
            LOGGER.error(ex.getMessage(), ex);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity completeOrder(OrderCompleteRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            if (request.getOrderId() != null) {
                String orderId = request.getOrderId();
                List<Order> listOrder = new ArrayList<>();
                Map<String, Object> argument = new HashMap<>();
                List<Value.MapValue> argumentFilter = new ArrayList<>();

                Map<String, Object> f = new HashMap<>();
                f.put("field", "OrderId");
                f.put("value", orderId);
                f.put("operator", "=");
                argumentFilter.add(new Value.MapValue(f));

                List<Value.MapValue> argumentSorters = new ArrayList<>();
                Map<String, Object> s1 = new HashMap<>();
                s1.put("sort_key", "OrderId");
                s1.put("order", "ASC");
                s1.put("type", "STRING");
                argumentSorters.add(new Value.MapValue(s1));

                argument.put("sorters", new Value.ListValue(argumentSorters));
                argument.put("filters", new Value.ListValue(argumentFilter));
                ResultSet resultSet = AerospikeFactory.getInstance()
                        .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));

                Iterator<Object> objecIterator = resultSet.iterator();
                while (objecIterator.hasNext()) {
                    ArrayList arrayList = (ArrayList) objecIterator.next();
                    arrayList.forEach((o) -> {
                        Order myOrder = new Order();
                        if (myOrder.parse((Map) o)) {
                            listOrder.add(myOrder);
                        }
                    });
                }
                if (listOrder.size() > 0) {
                    Order newOrder = new Order();
                    newOrder = (Order) listOrder.get(0);
//                    newOrder.setMessage(request.getMessage());
                    newOrder.setState(8L);
                    newOrder.setPaid(request.getPaid());

                    update(AerospikeFactory.getInstance().onlyUpdatePolicy,
                            DatabaseConstants.NAMESPACE, DatabaseConstants.ORDER_SET, request.getOrderId(), newOrder.toBins());

//                    notifi to Driver
                    notificationToDriver(request.getMessage(), request.getOrderId(), newOrder.getAccountIdDriver());
                    response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                    response.setData(Arrays.asList(newOrder));
                    response.setStatus(ResponseConstants.SUCCESS);

                } else {
                    response.setMessage("No record");
                    response.setStatus(ResponseConstants.SERVICE_FAIL);
                }
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC + ". Cannot get OrderId");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void notificationToDriver(String mess, String orderId, Long driverId) throws IOException, TimeoutException {

        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.DRIVER);
        NotificationObjectPushToDriver objectPushToDriver = new NotificationObjectPushToDriver();
        objectPushToDriver.setAccountId(driverId);//accountId
        objectPushToDriver.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);
        MsgNotifyDriver msgNotifyDriver = new MsgNotifyDriver();
        TitleObj titleObj = new TitleObj();
        titleObj.setTitle("VTGO");
        titleObj.setBody("Bạn nhận được thông báo mới");
        msgNotifyDriver.setNotification(titleObj);
        DataDriver dataDriver = new DataDriver();
        dataDriver.setOrderId(orderId);
        dataDriver.setMsg(mess);
        dataDriver.setType("6");
        msgNotifyDriver.setData(dataDriver);
        objectPushToDriver.setMessage(msgNotifyDriver);
        msgPushQueue.setData(Arrays.asList(objectPushToDriver));

        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);

    }

}
