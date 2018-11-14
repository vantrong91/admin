/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.api;

import com.vtgo.vn.admin.userinfo.BO.Category;
import com.vtgo.vn.admin.userinfo.request.SearchRequest;
import com.vtgo.vn.admin.userinfo.service.CategoryService;
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
 * @author tvhdh
 */
@RestController
@RequestMapping("v1/category")
@AllArgsConstructor
public class CategoryApi {
    private static final Logger LOGGER = Logger.getLogger(CategoryApi.class.getName());
    private final CategoryService categoryService;
    
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(@RequestBody SearchRequest request){
        LOGGER.debug("request /searchCategory: " + request);
        return categoryService.searchCategory(request);
    }
    
    @PostMapping(value = "/searchType", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity searchType(@RequestBody SearchRequest request){
        LOGGER.debug("request /searchTypeCategory" + request);
        return categoryService.searchString(request);
    }
    
    @PostMapping(value = "/get-by-id", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getById(@RequestBody Category request){
        LOGGER.debug("request /getById: " + request);
        return categoryService.getById(request);
    }
    
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@RequestBody Category request){
        LOGGER.debug("request /update: " + request);
        return categoryService.update(request);
    }
    
}
