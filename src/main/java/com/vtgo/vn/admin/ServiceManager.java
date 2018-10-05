/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin;

import com.vtgo.vn.admin.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
@Slf4j
public class ServiceManager {

    private static ServiceManager Instance;

    Logger logger = Logger.getLogger(ServiceManager.class);

    public synchronized static ServiceManager getInstance() {
        if (null == Instance) {
            Instance = new ServiceManager();
        }
        return Instance;
    }

    private ServiceManager() {
    }

    public void init() {
        try {
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
