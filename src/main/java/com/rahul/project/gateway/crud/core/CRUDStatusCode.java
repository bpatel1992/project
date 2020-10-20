package com.rahul.project.gateway.crud.core;

/**
 * Digipe Status Codes to notify Client of various States of User at DigipeServer
 * NOTE :
 * 9xxx : This Series denotes the Codes Corresponding to user Operations
 *
 * @author Rahul Malhotra
 */
public enum CRUDStatusCode {

    /**
     * This Error code denotes either the Phone Lost or Phone Stolen.
     */
    PHONE_LOST(9001, "ERR_9001"),

    /**
     * This Error Code denotes that the Account had been deactivated and no longer to be able to operate on.
     */
    ACCOUNT_INACTIVE(9002, "ERR_9002"),

    /**
     * This Error Code denotes that the Account Does Not Exist
     */
    ACCOUNT_NOT_EXIST(9003, "ERR_9003"),

    /**
     * This Error Code denotes that the User is Invalid
     */
    INVALID_USER(9004, "ERR_9004"),

    /**
     * This Error Code denotes that the Request is Invalid according to the Digipe Standards and business
     */
    INVALID_REQ(9005, "ERR_9005"),

    /**
     * ERR_9006-Denotes INTRUSION ATTCK
     * This Error Code denotes that the request parameters Validation Failed, and this is
     * possibly might be INTRUSION ATTACK
     */
    REQ_PARAM_VALIDATION_FAILED(9006, "ERR_9006");

    public int code;
    public String id;

    CRUDStatusCode(int code, String id) {
        this.code = code;
        this.id = id;
    }

    /**
     * The value corresponding to the respective code id here
     *
     * @return
     */
    public String value() {
        return this.id;
    }
}
