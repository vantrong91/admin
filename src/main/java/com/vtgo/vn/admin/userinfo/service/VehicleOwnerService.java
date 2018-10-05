/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.service;

import com.vtgo.vn.admin.userinfo.BO.VehicleOwner;
import com.vtgo.vn.admin.userinfo.request.VehicleOwnerSearchRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author HP
 */
public interface VehicleOwnerService {

    public ResponseEntity searchVehicleOwner(VehicleOwnerSearchRequest request);

    public ResponseEntity getVehicleOwnerById(VehicleOwnerSearchRequest request);

    public ResponseEntity update(VehicleOwner request);

    public ResponseEntity create(VehicleOwner request);

    public ResponseEntity delete(VehicleOwner request);
}
