package com.rahul.project.gateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class WebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses(){return new Class<?>[]{RootConfig.class};}

    @Override
    protected Class<?>[] getServletConfigClasses(){return new Class<?>[]{WebSocketChatConfig.class};}

    @Override
    protected String[] getServletMappings(){return new String[]{"/"};}

}