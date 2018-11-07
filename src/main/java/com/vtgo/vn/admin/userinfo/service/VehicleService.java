/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.Vehicle;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.request.VehicleOwnerSearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author HP
 */
public interface VehicleService {
     public ResponseEntity searchVehicle(SearchRequest request);

    public ResponseEntity getVehicleById(Vehicle request);

    public ResponseEntity update(Vehicle request);

    public ResponseEntity create(Vehicle request);
    
    public ResponseEntity delete(Vehicle request);    

    public ResponseEntity getListVehicleByOwner(Vehicle request);
    
    public ResponseEntity getListVehicleType(SearchRequest request);
}
