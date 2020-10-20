
package com.rahul.project.gateway.configuration;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public class BusinessException extends Exception {

    public BusinessException(String msg, Throwable t) {
        super(msg, t);
    }

    public BusinessException(String msg) {
        super(msg);
    }
}
