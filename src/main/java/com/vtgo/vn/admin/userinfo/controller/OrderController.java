/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Order;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.OrderService;
import java.util.ArrayList;
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

//                Iterator<Object> i = resultSet.iterator();
//                while (i.hasNext()) {
//                    try {
//                        KeyRecord myRecord = (KeyRecord) i.next();
//                        Order myOrder = new Order();
//                        LOGGER.debug(myRecord);
//                        if (myOrder.parse(myRecord.record)) {
//                            listOrder.add(myOrder);
//                        }
//                    } catch (Exception ex) {
//                    }
//                }
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
        return null;
    }

    @Override
    public ResponseEntity delete(Order request) {
        return null;

    }
}
