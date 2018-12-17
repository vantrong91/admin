/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

import com.jsoniter.output.JsonStream;
import com.vtgo.vn.admin.userinfo.BO.Driver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author viett
 */
public class SendNotify {

    public static void sendToDriver(Long driverId, String type, String title, String body, String content) throws TimeoutException, IOException {
        PropertyConfigurator.configure("./etc/log4j.properties");
        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.DRIVER);
        NotificationObjectPushToDriver objPushToDriver = new NotificationObjectPushToDriver();
        objPushToDriver.setAccountId(driverId);
        objPushToDriver.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);
        MsgNotifyDriver msgNotifyDriver = new MsgNotifyDriver();

        TitleObj titleObj = new TitleObj();
        titleObj.setTitle(title);
        titleObj.setBody(body);
        msgNotifyDriver.setNotification(titleObj);

        DataDriver dataDriver = new DataDriver();
        dataDriver.setMsg(content);
        dataDriver.setType(type);

        msgNotifyDriver.setData(dataDriver);
        objPushToDriver.setMessage(msgNotifyDriver);
        msgPushQueue.setData(Arrays.asList(objPushToDriver));

        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
    }

    public static void sendToGoodOwner(Long goodOwnerId, String type, String title, String body, String content) throws TimeoutException, IOException {
        PropertyConfigurator.configure("./etc/log4j.properties");
        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.OWNER);
        NotificationObjectPushToGoodsOwner objPushToOwner = new NotificationObjectPushToGoodsOwner();
        objPushToOwner.setAccountId(goodOwnerId);
        objPushToOwner.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);

        MsgNotifyGoodsOwner msgNotifyGoodOwner = new MsgNotifyGoodsOwner();

        TitleObj titleObj = new TitleObj();
        titleObj.setTitle(title);
        titleObj.setBody(body);
        msgNotifyGoodOwner.setNotification(titleObj);

        DataGoodsOwner dataGoodOwner = new DataGoodsOwner();
        dataGoodOwner.setMsg(content);
        dataGoodOwner.setType(type);

        msgNotifyGoodOwner.setData(dataGoodOwner);
        objPushToOwner.setMessage(msgNotifyGoodOwner);
        msgPushQueue.setData(Arrays.asList(objPushToOwner));

        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
    }

    public static void sendToVehicleOwner(Long vehicleOwnerId, String type, String title, String body, String content) throws TimeoutException, IOException {
        PropertyConfigurator.configure("./etc/log4j.properties");
        MsgPushQueue msgPushQueue = new MsgPushQueue();
        msgPushQueue.setTypeReceive(Constant.RECEIVE_TYPE.VEHICLE_OWNER);
        NotificationObjectPushToVehicleOwner objPushToOwner = new NotificationObjectPushToVehicleOwner();
        objPushToOwner.setAccountId(vehicleOwnerId);
        objPushToOwner.setNotifyType(Constant.NOTIFY_TYPE.FCM_PUSH);

        MsgVehicleOwner msgVehicleOwner = new MsgVehicleOwner();

        TitleObj titleObj = new TitleObj();
        titleObj.setTitle(title);
        titleObj.setBody(body);
        msgVehicleOwner.setNotification(titleObj);

        DataVehicleOwner dataVehicleOwner = new DataVehicleOwner();
        dataVehicleOwner.setMsg(content);
        dataVehicleOwner.setType(type);

        msgVehicleOwner.setData(dataVehicleOwner);
        objPushToOwner.setMessage(msgVehicleOwner);
        msgPushQueue.setData(Arrays.asList(objPushToOwner));

        String message = new String(JsonStream.serialize(msgPushQueue).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        Publish.publishMessage(message, Constant.QUEUE.RABBITMQ_EXCHANGE, Constant.QUEUE.KEY_CHANNEL_PUSH_FROM_ADMIN);
    }
}
