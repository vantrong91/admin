/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Language;
import com.aerospike.client.async.AsyncClient;
import com.aerospike.client.task.RegisterTask;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import com.vtgo.vn.admin.config.Config;
import lombok.AllArgsConstructor;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author HP
 */
@SpringBootApplication
public class Start {

    public static void main(String[] args) {
        PropertyConfigurator.configure("../etc/log4j.properties");
        Config.loadConfig("../etc/config.properties");
        AerospikeFactory.loadConfig("../etc/aerospike.conf");
        SpringApplication.run(Start.class, args);
    }

}
