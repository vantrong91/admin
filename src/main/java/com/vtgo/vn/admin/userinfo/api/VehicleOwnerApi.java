/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.VehicleOwner;
import com.vtgo.vn.admin.userinfo.request.VehicleOwnerSearchRequest;
import com.vtgo.vn.admin.userinfo.service.VehicleOwnerService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("v1/vehicle-owner")
@AllArgsConstructor
public class VehicleOwnerApi {

    private static final Logger LOGGER = Logger.getLogger(VehicleOwnerApi.class.getName());
    private final VehicleOwnerService vehicleOwnerService;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody VehicleOwnerSearchRequest request) {
        LOGGER.debug("request /searchVehicleOwner: " + request);
        return vehicleOwnerService.searchVehicleOwner(request);
    }

    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody VehicleOwnerSearchRequest request) {
        LOGGER.debug("request /getById: " + request);
        return vehicleOwnerService.getVehicleOwnerById(request);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody VehicleOwner request) {
        LOGGER.debug("request /update: " + request);
        return vehicleOwnerService.update(request);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity create(@RequestBody VehicleOwner request) {
        LOGGER.debug("request /create: " + request);
        return vehicleOwnerService.create(request);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody VehicleOwner request) {
        LOGGER.debug("request /delete: " + request);
        return vehicleOwnerService.delete(request);
    }

}
