/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.BO.Administration;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.AdministrationService;
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
 * @author tvhdh
 */
@Service
@AllArgsConstructor

public class AdministrationController extends BaseController implements AdministrationService {

    private static final Logger log = Logger.getLogger(AdministrationController.class.getName());

    @Override
    public ResponseEntity getProvince(SearchRequest request) {
        BaseResponse response = new BaseResponse();
        List<Administration> lstAdm = new ArrayList<>();
        try {
            Map<String, Object> argument = new HashMap<>();
            List<Value.MapValue> argumentFilter = new ArrayList<>();
            Long searchValue = request.getSearchParam2();
            
            if (searchValue != null) {
                Map<String, Object> f = new HashMap<>();
                f.put("field", "ID_CHA");
                f.put("value", searchValue);
                f.put("operator", "=");
                argumentFilter.add(new Value.MapValue(f));
            }
            List<Value.MapValue> argumentSorters = new ArrayList<>();
            Map<String, Object> s1 = new HashMap<>();
            s1.put("sort_key", "PK");
            s1.put("order", "ASC");
            s1.put("type", "LONG");
            argumentSorters.add(new Value.MapValue(s1));

            argument.put("sorters", new Value.ListValue(argumentSorters));
            argument.put("filters", new Value.ListValue(argumentFilter));
            ResultSet resultSet = AerospikeFactory.getInstance().aggregate(AerospikeFactory.getInstance().queryPolicy,
                    DatabaseConstants.NAMESPACE, DatabaseConstants.ADMINISTRATION_SET, "FILTER_RECORD", "FILTER_RECORD", Value.get(argument));
            if (resultSet != null) {
                Iterator<Object> obIterator = resultSet.iterator();
                while(obIterator.hasNext()){
                    ArrayList arrayList = (ArrayList) obIterator.next();
                    for (Object o : arrayList) {
                        Administration adm = new Administration();
                        if (adm.parse((Map) o)) {
                            lstAdm.add(adm);
                        }
                    }
                }
            }
            response.setData(lstAdm);
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity getById(Administration request) {
        BaseResponse response = new BaseResponse();
        try {
            Record rec = getById(DatabaseConstants.NAMESPACE, DatabaseConstants.ADMINISTRATION_SET, request.getPk());
            response.setStatus(ResponseConstants.SUCCESS);
            response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
            if (rec != null) {
                Administration adm = new Administration();
                adm.parse(rec);
                adm.setPk(request.getPk());
                response.setData(Arrays.asList(adm));
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(ResponseConstants.SERVICE_FAIL);
            response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
