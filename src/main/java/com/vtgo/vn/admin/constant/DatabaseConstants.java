package com.vtgo.vn.admin.constant;

public class DatabaseConstants {

    public static String NAMESPACE = "vgo";
    public static String VEHICLE_SET = "vehicle";
    public static String INSURANCE_ORDER_SET = "insuranceOrder";
    public static String VEHICLE_OWNER_SET = "vehicleOwner";
    public static String CATEGORY_SET = "category";
    public static String DRIVER_SET = "driver";
    public static String GOOD_OWNER = "goodsOwner";
    public static String ACCOUNT_SET = "account";
    public static String BANK_SET = "bankAccount";
    public static String ORDER_SET = "order";
    public static String QUOTATION = "quotation";
    public static String BALANCE = "balance";
    public static String BALANCE_HIS_SET = "history";
    public static String ACCOINT_MAN_SET = "account";
    public static String TRANSFER_FEE_SET = "transferFee";
    public static String POLICY_SET = "policy";
    public static String ADMINISTRATION_SET = "administration";
    public static String BANKADMIN_SET = "bankAdmin";

    public class Command {

        public static final int QUERY_ACCOUNT = 1;
        public static final int REGISTER_ACCOUNT = 2;
        public static final int CREATE_BALANCE = 3;
        public static final int CHANGE_BALANCE = 4;
        public static final int CHANGE_BALANCE_TRANSACTION = 5;
        public static final int REGISTER_TRANSPORTATION = 6;
        public static final int QUERY_VEHICLE = 7;
        public static final int CREATE_ORDER = 8;
        public static final int QUERY_ORDER = 9;
        public static final int REGISTER_VEHICLE_OWNER = 10;
        public static final int QUERY_VEHICLE_OWNER = 11;
        public static final int LOGIN = 12;

        public static final int UPDATE_TRANSPORTATION = 13;

        public static final int ROUTE_REGISTER = 20;
        public static final int VEHICLE_SEARCH = 21;
        public static final int LOCATION_UPDATER = 22;
        public static final int CHECK_VEHICLE_LOCATION = 23;

        public static final int VEHICLE_DRIVER_SEARCH = 40;
        public static final int GET_ORDER_BY_VEHICLE = 41;
        public static final int VEHICLE_OWNER_GET_DETAIL = 42;
        public static final int GET_DRIVER_BY_ID = 43;
        public static final int GET_ORDER_BY_ID = 44;
        public static final int CHANGE_DRIVER = 45;
        public static final int SEND_ROUTE_TO_DRIVER = 46;
        public static final int REGISTER_EMPTY = 47;
        public static final int GET_LIST_ORDER = 48;
        public static final int CONFIRM_ORDER = 49;
    }

    public class ResultCode {

        public static final int SUCCESS = 0;
        public static final int FAIL = 1;
        public static final int INVALID_INPUT = 2;

    }

    public class RoutingResultCode {

        public static final int EXISTED_ROUTING = 2;
        public static final int NOT_EXISTED_VEHICLE = 3;
    }

    public class ResultText {

        public static final String SUCCESS = "SUCCESS";
        public static final String MYSQL_EXCEPTION = "MYSQL_EXCEPTION";
        public static final String FAIL = "ERROR";
        public static final String EXISTED_ROUTING = "ROUTING_EXISTED";
        public static final String NOT_EXISTED_VEHICLE = "NOT_EXISTED_VEHICLE";

    }

}
