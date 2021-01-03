package com.rahul.project.gateway;

import com.rahul.project.gateway.configuration.ApplicationExitUtil;
import com.rahul.project.gateway.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@ComponentScan(basePackages = "com.rahul")
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.rahul.project.gateway.repository","com.rahul.project.gateway.chat.repository"}, repositoryBaseClass = BaseRepositoryImpl.class)
@EnableJpaAuditing
@EnableAsync
public class GatewayApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(GatewayApplication.class, args);
        ApplicationExitUtil.waitForKeyPressToCleanlyExit(ctx);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GatewayApplication.class);
    }

}

