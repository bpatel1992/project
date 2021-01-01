package com.rahul.project.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.service.AuthorizationScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.security.oauth2.client.registration.my-client.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.my-client.client-secret}")
    private String clientSecret;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rahul.project.gateway.controller"))
                .paths(PathSelectors.any()).build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot 2.0 Backend for PP")
                .description("Service Description for the project")
                .version("version 1.0")
                .contact(new Contact("Rahul Malhotra", "https://www.facebook.com/malhotra.rah",
                        "rahul.malhotra@live.com")).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization ", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }


}