/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

import com.jsoniter.output.JsonStream;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Publish {

    private static final Logger logger = Logger.getLogger(Publish.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        PropertyConfigurator.configure("./etc/log4j.properties");
        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.DRIVER);
        NotificationObjectPushToDriver objectPushToDriver = new NotificationObjectPushToDriver();
        objectPushToDriver.setAccountId(13302L);//accountId 
        objectPushToDriver.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);
        MsgNotifyDriver msgNotifyDriver = new MsgNotifyDriver();
        TitleObj titleObj = new TitleObj();
        titleObj.setTitle("VTGO");
        titleObj.setBody("Bạn nhận được thông báo mới");
        msgNotifyDriver.setNotification(titleObj);
        DataDriver dataDriver = new DataDriver();
        dataDriver.setOrderId("ĐHxxxxxxxxx");
        dataDriver.setMsg("Admin xác nhận thanh toán đơn hàng ĐHxxxxxxxxx");
        dataDriver.setType("7");
        msgNotifyDriver.setData(dataDriver);
        objectPushToDriver.setMessage(msgNotifyDriver);
        msgPushQueue.setData(Arrays.asList(objectPushToDriver));

        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
    }

    public static void publishMessage(String message, String exchange, String routingKey) throws TimeoutException, IOException {
        if (connection == null || !connection.isOpen()) {
            Connection rabbitMQConnection = getRabbitMQConnection();
        }
        logger.info("connection: " + connection);
        Channel channel = connection.createChannel();
//<editor-fold defaultstate="collapsed" desc="comment">
//        channel.exchangeDeclare("exchangname", BuiltinExchangeType.DIRECT);
//        channel.queueDeclare("queuename", true, false, false, null);
//</editor-fold>
        byte[] messageBodyBytes = message.getBytes();
        channel.basicPublish(exchange, routingKey, null, messageBodyBytes);

    }

    private static Connection connection;

    private static Connection getRabbitMQConnection() throws TimeoutException, IOException {
        if (connection != null) {
            return connection;
        }
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("103.90.220.107");
        factory.setUsername("guest");
        factory.setPassword("guest");
        connection = factory.newConnection();
        return connection;
    }

}
