/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.constant;

/**
 *
 * @author tuantv
 */
public class ResponseConstants {

    public static int SERVICE_ERROR = 1;
    public static int SERVICE_SUCCESS = 200;
    public static int SERVICE_FAIL = 403;
    public static int SERVICE_TOKEN_INVALID = 401;
    public static int SERVICE_LOGIN_FAIL = 104;
    public static int SERVICE_CUSTOMER_NOT_FOUND = 404;
    public static int SERVICE_TIMEOUT = 504;
    public static int SERVICE_INTERNAL_SERVER_ERROR = 500;
    public static int SERVICE_PASSWORD_INVALID = 5;
    public static int SERVICE_CONTRAINT_VIOLATION_ERROR = 2292;
    public static int TOKEN_EXPIRE = 99;

    public static String SERVICE_NOT_FOUND = "RECORD_NOT_FOUND";
    public static String SERVICE_GET_BALANCE_FAIL = "Cannot get balance";
    public static String SERVICE_LOGIN_FAILED_DESC = "The username or password is incorrect. Please input again ";
    public static String SERVICE_TOKEN_INVALID_DESC = "Token invalid";
    public static String SERVICE_CUSTOMER_NOT_FOUND_DESC = "Customer not found";
    public static String SERVICE_OTP_INCORRECT_DESC = "OTP code incorrect";
    public static String SERVICE_OLD_PASSWORD_INCORRECT_DESC = "Old password incorrect";
    public static String SERVICE_OTP_EXPIRED_DESC = "OTP expired";
    public static String SERVICE_INTERNAL_SERVER_ERROR_DESC = "Interal Server erorr";
    public static String SERVICE_GET_LIST_VEHICLE_FAIL_DESC = "Get list vehicle fail";
    public static String SERVICE_VEHICLE_OWNER_NOT_FOUND = "Vehicle owner not found";
    public static String SERVICE_ACCOUNT_NOT_FOUND = "Account not found";
    public static String SERVICE_INSURANCE_ORDER_NOT_FOUND = "Insurance order not found";
    public static String SERVICE_CATEGORY_NOT_FOUND = "Category not found";

    public static String SERVICE_SUCCESS_DESC = "Successful";
    public static String SERVICE_FAIL_DESC = "Fail";
    public static String SERVICE_TIMEOUT_DESC = "Execute request was timeout";
    public static String SERVICE_EXECUTE_ERROR = "SERVICE_EXECUTE_ERROR";
    public static String SERVICE_NOT_AUTHEN = "SERVICE_NOT_AUTHEN";
    public static String SERVICE_NOT_AUTHEN_DESC = "Username or password is invalid.";
    public static String SERVICE_NOT_PERMISSION_DESC = "Username does not have permission to access service.";
    public static String SERVICE_OVER_TPS = "SERVICE_OVER_TPS";
    public static String SERVICE_OVER_TPS_DESC = "Number of request is over TPS";
    public static String TRANSACTION_ID_NOT_EXIST_DESC = "SERVICE_TRANSACTION_ID_NOT_EXIST";
    public static String TRANSACTION_HAS_NOT_BEEN_SUCCESSFUL_DESC = "Transaction has not been successful";
    public static String SERVICE_PASSWORD_INVALID_DESC = "Password not strong";
    public static String SERVICE_DEVICE_TOKEN_INVALID_DESC = "Device token invalid";
    public static String SERVICE_VALIDATION_FAIL = "Validate fail";
    public static String SERVICE_CONTRAINT_VIOLATION_ERROR_DESC = "integrity constraint violated - child record found";
    public static String SOME_PARAMETER_INCORRCET_DESC = "Some parmeter icorrect";
    public static String INPUT_FILE_INCORRCET_DESC = "Input files incorrect";

    public static final int SUCCESS = 0;
    public static final int UNKNOW_ERROR = 1;
    public static final int WRONG_DATA_FORMAT = 2;

    public static final int PHONE_NUMBER_NOT_FOUND = 3;
    public static final int IN_CORRECT_PASSWORD = 4;

    public static final int REQUEST_TIMEOUT = 5;
    public static final int PERMISSION_DENY = 403;
    public static final int SOME_PARAMETER_INCORRCET = 301;
    public static final int INPUT_FILE_INCORRCET = 302;

}
