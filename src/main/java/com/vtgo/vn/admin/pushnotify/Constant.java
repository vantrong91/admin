/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.pushnotify;

/**
 *
 * @author Trong Van
 */
public class Constant {

    public static class NOTIFY_TYPE {

        public static final int EMAIL = 1;
        public static final int FCM_PUSH = 2;
    }

    public static class RECEIVE_TYPE {

        public static final int DRIVER = 1;
        public static final int OWNER = 2;
        public static final int VEHICLE_OWNER = 3;
    }

    public static class QUEUE {

        public static final String RABBITMQ_EXCHANGE = "adminevt";
        public static final String KEY_CHANNEL_PUSH_FROM_ADMIN = "admin_push_noti";
    }

}
