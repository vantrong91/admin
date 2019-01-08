package com.vtgo.vn.admin.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class Config {

    private static final Logger logger = Logger.getLogger(Config.class);

    public static int port;
    public static String contextPath = "";
    public static String hostRabbit = "";
    public static String userRabbit = "";
    public static String passRabbit = "";

    public static void loadConfig(String path) {
        FileReader fr = null;
        Properties pro = null;
        try {
            fr = new FileReader(path);
            pro = new Properties();
            pro.load(fr);
            port = Integer.parseInt(pro.getProperty("port", "8086").trim());
            contextPath = pro.getProperty("contextPath", "vtgo-admin").trim();
            hostRabbit = pro.getProperty("hostRabbit", "172.16.220.71").trim();
            userRabbit = pro.getProperty("userRabbit", "vtgo").trim();
            passRabbit = pro.getProperty("passRabbit", "vtgo@vtgo@123").trim();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (pro != null) {
                pro.clear();
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
    }

}
