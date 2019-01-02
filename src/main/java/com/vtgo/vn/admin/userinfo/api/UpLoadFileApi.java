/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.controller.UpLoadFileController;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author viett
 */
@RestController
@RequestMapping("v1/upload")
@AllArgsConstructor
public class UpLoadFileApi {

    private static final Logger LOGGER = Logger.getLogger(UpLoadFileApi.class.getName());
    private final UpLoadFileController upload;

    @PostMapping(value = "/image-order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity order(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("image-order uploading...");
        return upload.uploadFile(files, "IMAGE-ORDER");
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity avatar(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("avatar uploading...");
        return upload.uploadFile(files, "AVATA");
    }

    @PostMapping(value = "/shk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity shk(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("shk uploading...");
        return upload.uploadFile(files, "SHK");
    }

    @PostMapping(value = "/cmnd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity cmnd(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("cmnd uploading...");
        return upload.uploadFile(files, "CMND");
    }
    
    @PostMapping(value = "/cmnd-owner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity cmndOwner(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("cmnd uploading...");
        return upload.uploadFile(files, "CMND-OWNER");
    }

    @PostMapping(value = "/gplx", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity gplx(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("gplx uploading...");
        return upload.uploadFile(files, "GPLX");
    }

    @PostMapping(value = "/acd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity acd(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("acd uploading...");
        return upload.uploadFile(files, "ACD");
    }
    
    @PostMapping(value = "/gpkdvt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity gpkdvt(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("gpkdvt uploading...");
        return upload.uploadFile(files, "GPKDVT");
    }
    
    @PostMapping(value = "/gpdhvt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity gpdhvt(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("gpdhvt uploading...");
        return upload.uploadFile(files, "GPDHVT");
    }
    @PostMapping(value = "/dkkd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity dkkd(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("dkkd uploading...");
        return upload.uploadFile(files, "DKKD");
    }
    @PostMapping(value = "/dauct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity dauct(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("dauct uploading...");
        return upload.uploadFile(files, "DAUCT");
    }
    @PostMapping(value = "/dkyxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity dkyxe(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("dkyxe uploading...");
        return upload.uploadFile(files, "DKYXE");
    }
    @PostMapping(value = "/dkiemxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity dkiemxe(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("dkiemxe uploading...");
        return upload.uploadFile(files, "DKIEMXE");
    }
    @PostMapping(value = "/bhdsxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity bhdsxe(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("bhdsxe uploading...");
        return upload.uploadFile(files, "BHDSXE");
    }
    @PostMapping(value = "/bhhhxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity bhhhxe(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("bhhhxe uploading...");
        return upload.uploadFile(files, "BHHHXE");
    }
    @PostMapping(value = "/gxntbgs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity gxntbgs(@RequestParam("files") MultipartFile[] files) {
        LOGGER.debug("gxntbgs uploading...");
        return upload.uploadFile(files, "GXNTBGS");
    }
    @PostMapping(value = "aphxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity aphxe(@RequestParam("files") MultipartFile[] files){
        LOGGER.debug("aphxe uploading...");
        return upload.uploadFile(files, "APHXE");
    }
    @PostMapping(value = "achxe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity achxe(@RequestParam("files") MultipartFile[] files){
        LOGGER.debug("achxe uploading...");
        return upload.uploadFile(files, "ACHXE");
    }
    
    
}
