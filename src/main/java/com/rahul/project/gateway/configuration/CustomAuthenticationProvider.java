package com.rahul.project.gateway.configuration;


import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.IUserDao;
import com.rahul.project.gateway.dto.TokenInfoResponseDTO;
import com.rahul.project.gateway.model.Authority;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.service.api.ApiServiceFactory;
import com.rahul.project.gateway.service.api.GoogleApi;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import retrofit2.Call;
import retrofit2.Response;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


@Qualifier("CustomAuthenticationProvider")
@TransactionalService
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    Environment environment;

    /*    @Autowired
        private TokenStore tokenStore;*/
    @Autowired
    Translator translator;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommonUtility commonUtility;
    @Autowired
    ApiServiceFactory apiServiceFactory;
    @Autowired
    private IUserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws OAuth2Exception {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        String loginType = "self";
//        userType = "ADMIN",
       /* if (((Map) authentication.getDetails()).get("user_type") != null)
            userType = ((Map) authentication.getDetails()).get("user_type").toString();*/


        if (((Map) authentication.getDetails()).get("login_type") != null)
            loginType = ((Map) authentication.getDetails()).get("login_type").toString();
      /*  //authority
        String authority = "ROLE_ADMIN";
        if (userType != null) {
            if ("customer".equalsIgnoreCase(userType))
                authority = "ROLE_CUSTOMER";
            else if ("partner".equalsIgnoreCase(userType))
                authority = "ROLE_PARTNER";
        }*/

        // login type self
        if (loginType != null) {
            if ("gmail".equalsIgnoreCase(loginType))
                loginType = "gmail";
            else if ("facebook".equalsIgnoreCase(loginType))
                loginType = "facebook";
        }


        User user;
        try {
            if (((Map) authentication.getDetails()).get("random_key") != null)
                user = userRepository.getByRandomKey(((Map) authentication.getDetails()).get("random_key").toString());
            else if ("gmail".equalsIgnoreCase(loginType) || "facebook".equalsIgnoreCase(loginType)) {
                TokenInfoResponseDTO tokenInfoResponseDTO;
                Call<TokenInfoResponseDTO> oauthResponseCall;
                if ("facebook".equalsIgnoreCase(loginType)) {
                    oauthResponseCall = apiServiceFactory.getRetrofitFacebook().create(GoogleApi.class).authenticateFacebook(password, "email");
                } else
                    oauthResponseCall = apiServiceFactory.getRetrofitGoogle().create(GoogleApi.class).authenticate(password);
                Response<TokenInfoResponseDTO> oauthResponseResponse = oauthResponseCall.execute();
                if (oauthResponseResponse.isSuccessful()) {
                    tokenInfoResponseDTO = oauthResponseResponse.body();
                } else {
                    throw new BusinessException(translator.toLocale("token.invalid"));
                }
                if (tokenInfoResponseDTO != null && name.equalsIgnoreCase(tokenInfoResponseDTO.getEmail()))
                    user = userDao.findByUserName(name);
                else {
                    AuthException authException = new AuthException();
                    authException.setErrorDescription(translator.toLocale("email is not verified or the token belong to some other user"));
                    throw new CustomException(authException);
                }
            } else
                user = userDao.findByUserName(name, password, loginType);
        } catch (Exception e) {
            AuthException authException = new AuthException();
            authException.setErrorDescription(translator.toLocale(e.getMessage()));
            throw new CustomException(authException);
        }
        if (user == null) {
            AuthException authException = new AuthException();
            authException.setErrorDescription(translator.toLocale("user.not.found", new String[]{name}));
            throw new CustomException(authException);
        }
        if (!user.getActivated()) {
            AuthException authException = new AuthException();
            authException.setErrorDescription(translator.toLocale("user.not.activated", new String[]{name}));
            throw new CustomException(authException);
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority auth : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth.getName());
            grantedAuthorities.add(grantedAuthority);
        }

        String timeZone = "Asia/Kolkata";
        if (((Map) authentication.getDetails()).get("time_zone") != null)
            timeZone = ((Map) authentication.getDetails()).get("time_zone").toString();
        try {
            user.setLastVisit(commonUtility.getDateByTimeZoneDate(timeZone));
        } catch (ParseException e) {
            user.setLastVisit(new Date());
        }
        userRepository.save(user);
        //check for access token and refresh token and invalidate it
        /*Collection<OAuth2AccessToken> tokens =
                tokenStore.findTokensByClientIdAndUserName
                        (environment.getRequiredProperty("spring.security.oauth2.client.registration.my-client.client-id"), user.getId().toString());
        if (!CollectionUtils.isEmpty(tokens)) {
            for (OAuth2AccessToken oAuth2AccessToken : tokens) {
                OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
                tokenStore.removeAccessToken(oAuth2AccessToken);
                tokenStore.removeRefreshToken(oAuth2RefreshToken);
            }
        }*/

        return new UsernamePasswordAuthenticationToken(user.getId(), "", grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
