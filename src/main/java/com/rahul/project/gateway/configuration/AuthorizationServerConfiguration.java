package com.rahul.project.gateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Environment environment;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(environment.getRequiredProperty("spring.security.oauth2.client.registration.my-client.client-id"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                // .secret("{bcrypt}$2a$16$jLzKwu1oP4XV.IJ0VsR2EeSjfw4O4iq6Hg9/cnj8g327V7dq.xRUG")
                .secret("{noop}" + environment.getRequiredProperty("spring.security.oauth2.client.registration.my-client.client-secret"))
                .resourceIds("my_rest_api")
//				.accessTokenValiditySeconds(120);//Access token is only valid for 2 minutes.
                //.refreshTokenValiditySeconds(120);//Refresh token is only valid for 10 minutes.
                .accessTokenValiditySeconds(120).//Access token is only valid for 2 minutes.
                refreshTokenValiditySeconds(Integer.MAX_VALUE);//Refresh token is only valid for 10 minutes.
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).userDetailsService(userDetailsService).authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //oauthServer.allowFormAuthenticationForClients().realm(REALM + "/client");
        oauthServer.allowFormAuthenticationForClients();
    }


    @Bean
    @Primary
    public AuthorizationServerTokenServices tokenServices() {

        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenEnhancer(tokenEnhancer());
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setReuseRefreshToken(true);

        return tokenServices;
    }


    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public DefaultAccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }


    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
