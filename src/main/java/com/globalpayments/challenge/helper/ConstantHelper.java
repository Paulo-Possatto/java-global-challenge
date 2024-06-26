package com.globalpayments.challenge.helper;

/**
 * This class is used to define constants in general, to avoid repeating yourself
 */
public class ConstantHelper {

    public static final String MAIN_PATH = "/v1/challenge";
    public static final String CUSTOMER_PATH = "/customer";
    public static final String CAR_PATH = "/car";

    public static final String CAR_TYPE_NOT_EXIST = "The selected car type is not available, available choices: " +
            "\"Premium\", \"SUV\" and \"Small\"";
    public static final String CAR_NAME_DOES_NOT_EXIST = "The car does not exists on the database";
    public static final String CUSTOMER_DOES_NOT_EXIST = "The selected customer does not exists on the database";
}
