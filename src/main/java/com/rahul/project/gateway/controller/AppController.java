package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.CustomerProfileDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.service.UserService;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserService userService;

    @Autowired
    Translator translator;

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/oauth/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) throws BusinessException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            if (accessToken == null)
                throw new BusinessException(translator.toLocale("invalid.token", new String[]{tokenValue}));
            OAuth2RefreshToken oAuth2RefreshToken = accessToken.getRefreshToken();
            tokenStore.removeAccessToken(accessToken);
            tokenStore.removeRefreshToken(oAuth2RefreshToken);

        }
    }

    @GetMapping(value = {"/oauth2/api/newsletter/subscribe", "/api/newsletter/subscribe"}
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO user(@RequestParam(value = "email", required = false) String email) throws Exception {
        return userService.userNewsLetterSubscription(email, new ResponseDTO());
    }

    @GetMapping(value = "/oauth2/api/user/data", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CustomerProfileDTO getUserData() throws Exception {
        return userService.getUserData();
    }

}
