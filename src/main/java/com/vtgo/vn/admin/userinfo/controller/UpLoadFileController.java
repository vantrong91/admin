/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.controller;

import java.util.*;
import com.vtgo.vn.admin.base.BaseController;
import com.vtgo.vn.admin.base.BaseResponse;
import com.vtgo.vn.admin.constant.ResponseConstants;
import com.vtgo.vn.admin.userinfo.service.UpLoadFileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author viett
 */
@Service
@AllArgsConstructor
public class UpLoadFileController extends BaseController implements UpLoadFileService {
    
    public final Logger log = Logger.getLogger(UpLoadFileController.class.getName());
    
    @Override
    public ResponseEntity uploadFile(MultipartFile[] files, String folder) {
        BaseResponse response = new BaseResponse();
        ArrayList<String> lstStatus = new ArrayList<String>();
        FileOutputStream fileOutputStream = null;
//        String path = "/home/haivinh/IMAGE/" + folder;
        String path = "/u02/IMAGE/" + folder;

        for (MultipartFile multipartFile : files) {
            String fileName = multipartFile.getOriginalFilename();

//            File uploadedFile = new File("D:/uploadDir/a", fileName);
            File uploadedFile = new File(path, fileName);
            try {
                uploadedFile.createNewFile();
                fileOutputStream = new FileOutputStream(uploadedFile);
                fileOutputStream.write(multipartFile.getBytes());
                fileOutputStream.close();
                
                response.setMessage(ResponseConstants.SERVICE_SUCCESS_DESC);
                response.setStatus(ResponseConstants.SERVICE_SUCCESS);
                lstStatus.add(fileName + ": successfully!");
            } catch (IOException e) {
                log.error(e.getMessage());
                response.setMessage(ResponseConstants.SERVICE_FAIL_DESC);
                response.setStatus(ResponseConstants.SERVICE_FAIL);
                lstStatus.add(fileName + ": error!");
            }
            
        }
        log.debug("upload to " + folder + "success. Data: " + lstStatus.toString());
        response.setData(lstStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
