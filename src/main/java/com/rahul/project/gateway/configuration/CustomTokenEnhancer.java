package com.rahul.project.gateway.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

       /* List<Object[]> usrInfo = getUSerId(authentication.getName());
        logger.info("User id===" + usrInfo.get(0));
        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("userId", (usrInfo.get(0))[0]);
        additionalInfo.put("email", (usrInfo.get(0))[1]);
        additionalInfo.put("userName", (usrInfo.get(0))[2]);

        Integer userStage = Integer.valueOf((usrInfo.get(0))[3].toString());
        additionalInfo.put("userStage", userStage.toString());

        String status = String.valueOf(usrInfo.get(0)[4]);

        String userType = String.valueOf(usrInfo.get(0)[5]);


        DefaultOAuth2AccessToken accessToken2 = (DefaultOAuth2AccessToken) accessToken;
        additionalInfo.put("accessToken", accessToken2.getValue());
        additionalInfo.put("refreshToken", accessToken2.getRefreshToken().getValue());

        additionalInfo.put("response_code", "0000");
        additionalInfo.put("responseCode", "0000");
        additionalInfo.put("responseDescription", "You have successfully logged into the application.");
        additionalInfo.put("status", status);


        updateOauthEntry(additionalInfo);

        if (userStage == 1) {
            additionalInfo.put("response_code", "0000");
            additionalInfo.put("responseMessage", "You have successfully logged into the application");
            additionalInfo.put("responseCode", "0000");
            additionalInfo.put("responseDescription", "You have successfully logged into the application");
        }

        accessToken2.setAdditionalInformation(additionalInfo);*/

        return accessToken;
    }
}