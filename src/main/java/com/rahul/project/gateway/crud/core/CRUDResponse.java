package com.rahul.project.gateway.crud.core;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Generalized Response that must be used as a reponse to client request
 * <p>
 * isError : if the request is not successful responseResult : holds response
 * result responseError : holds response error if isError is true
 *
 * @author Rahul Malhotra
 */
public class CRUDResponse {

    public boolean isError;
    public Map<String, Object> responseResult;
    public CRUDResponseError responseError;

    public CRUDResponse(Object result) {
        this.responseResult = new HashMap<>();
        this.responseResult.put("data", result);
    }

    public CRUDResponse(boolean isError, String code) {
        this.isError = isError;
        if (isError) {
            this.responseError = CRUDResponseError.builder().build(new Timestamp(System.currentTimeMillis()), code);
        }
    }
}
