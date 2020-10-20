package com.rahul.project.gateway.configuration;


import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class CustomException extends OAuth2Exception implements Serializable {

    CustomException(AuthException authException) {

        super(authException.getErrorDescription());
        /*if (authException.getUserType() != null && !authException.getUserType().equals("")) {
            addAdditionalInformation("userType", authException.getUserType());
        }*/
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "403";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpServletResponse.SC_FORBIDDEN;
    }
}