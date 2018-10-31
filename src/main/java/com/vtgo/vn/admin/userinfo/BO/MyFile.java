/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author viett
 */
public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private MultipartFile multipartFile;
    private String description;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
