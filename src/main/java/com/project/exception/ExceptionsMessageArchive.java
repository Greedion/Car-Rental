package com.project.exception;


public class ExceptionsMessageArchive {

    /**
     * Brand Controller
     */
    public static final String BRAND_C_ID_COULD_NOT_BE_NULL = "Id could not be null";
    public static final String BRAND_C_EXPECTED_DATA_NOT_FOUND = "Attempt to remove brand by id that does not exist in database.";
    public static final String BRAND_C_NOT_FOUND_EXCEPTION = "Attempt to get brand by id that does not exist in database.";
    public static final String BRAND_C_PARSE_EXCEPTION = "Attempt parse String to Long.";


    /**
     *  Car Controller
     */
    public static final String CAR_C_ID_COULD_NOT_BE_NULL = "Id could not be null";
    public static final String CAR_C_EXPECTED_DATA_NOT_FOUND = "Attempt to remove car by id that does not exist in database.";
    public static final String CAR_C_NOT_FOUND_EXCEPTION = "Attempt to get car by id that does not exist in database.";
    public static final String CAR_C_PARSE_EXCEPTION = "Attempt parse String to Long.";


    /**
     *  Rental Controller
     */
    public static final String RENTAL_C_NULL_USERNAME_EXCEPTION = "Attempt to create reservation with outdated login token. Or user doesn't exist.";

    /**
     * User Controller
     */
    public static final String USER_C_NULL_USERNAME_EXCEPTION = "Attempt to create reservation with outdated login token. Or user doesn't exist.";

    /**
     *  Auth Service
     */
    public static final String AUTH_S_INCORRECT_DATA_EXCEPTION = "Received incorrect logging data.";

    /**
     * Brand Service
     */
    public static final String BRAND_S_MODIFY_BRAND_NON_EXISTS_ID = "Attempt to modify the brand using a non-existent id";
    public static final String BRAND_S_GET_BRAND_NON_EXISTS_ID = "Attempt to get the brand using a non-existent id";
    public static final String BRAND_S_REMOVE_BRAND_WITH_ASSIGNING_BIND = "Attempt remove brand with assigned binding.";
    public static final String BRAND_S_DELETE_BRAND_NON_EXISTS_ID = "Attempt to delete the brand using a non-existent id";

    /**
     * Car Service
     */
    public static final String CAR_S_EXCEPTION_ALERT = "Wrong input data format Exception";

    /**
     * Rental Service
     */
    public static final String RENTAL_S_DATE_FORMAT_EXCEPTION = "Input Data is empty";
    public static final String RENTAL_S_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String RENTAL_S_PARSE_EXCEPTION = "PARSE FROM METHOD HowManyHours Exception";
    public static final String RENTAL_S_FAILED_CREATE_ACCOUNT_EXCEPTION = "Failed to create an account";
    public static final String RENTAL_S_EMPTY_VALUE_FOR_MONEY_EXCEPTION = "Attempt used empty value for money or carPrice.";
    public static final String RENTAL_S_PARSE_TO_BIG_DECIMAL_EXCEPTION = "Attempt to parse String to BigDecimal.";
    public static final String RENTAL_S_GET_MONEY_FROM_NON_EXISTS_USERNAME = "Attempt get money value from non-existent username.";
    public static final String RENTAL_S_PARSE_STRING_TO_DATE_EXCEPTION = "Attempt parse String to Date format.";
    public static final String RENTAL_S_GET_CAR_PRICE_PER_HOUR_FROM_NON_EXISTS_CAR = "Attempt get carPricePerHour value from non-existent carID.";

    /**
     *  User Service
     */
    public static final String USER_S_FORMAT_EXCEPTION = "Money parse format exception";
    public static final String USER_S_DEFAULT_USER_ROLE = "ROLE_USER";
    public static final String USER_S_ACCOUNT_NOT_EXISTS_EXCEPTION = "Account with this username doesn't exist";
    public static final String USER_S_WRONG_ACCOUNT_ROLE_STATUS = "Received wrong DEFAULT_USER_ROLE or account with this username doesn't exist";

    /**
     *  Global Exception Handler
     */
    public static final String G_E_HANDLER_VALIDATION_FAILED_MESSAGE ="Validation Failed.";
}
