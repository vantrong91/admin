/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.BalanceHis;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.BalanceHisService;
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
public class BalanceHisController extends BaseController implements BalanceHisService {

    private static final Logger logger = Logger.getLogger(BalanceHisController.class.getName());

    @Override
    public ResponseEntity searchBalanceHis(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<BalanceHis> listBalance = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            String searchVal = request.getSearchParam();
            if (searchVal != null && !searchVal.isEmpty()) {

            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s = new HashMap<>();
            s.put("sort_key", "HisId");
            s.put("order", "ASC");
            s.put("type", "STRING");
            argumentSorters.add(new Value.MapValue(s));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance()
                    .aggregate(AerospikeFactory.getInstance().queryPolicy, DatabaseConstants.NAMESPACE, DatabaseConstants.BALANCE_HIS_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
//                 Iterator<Object> objectIterator = resultSet.iterator();
//                while (objectIterator.hasNext()) {
//                    ArrayList arrayList = (ArrayList) objectIterator.next();
//                    for (Object o : arrayList) {
//                        BalanceHis balanceHis = new BalanceHis();
//                        if (balanceHis.parse((Map) o)) {
//                            listBalance.add(balanceHis);
//                        }
//                    }
//                }
            }
             response.setData(listBalance);
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

    @Override
    public ResponseEntity getBalanceHisById(BalanceHis request
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity update(BalanceHis request
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity create(BalanceHis request
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity delete(BalanceHis request
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
