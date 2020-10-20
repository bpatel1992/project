
package com.rahul.project.gateway.configuration;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public class UserNotActivatedException extends AuthenticationException {


    public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
