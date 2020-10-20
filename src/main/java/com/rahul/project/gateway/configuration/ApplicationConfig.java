package com.rahul.project.gateway.configuration;

import okhttp3.logging.HttpLoggingInterceptor;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAutoConfiguration
public class ApplicationConfig {

    @Autowired
    EntityManagerFactory entityManagerFactory;

/*    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }*/

/*    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    HibernateSearchService hibernateSearchService;

    @Bean(name = "hibernateSearchService")
    HibernateSearchService hibernateSearchService() {
        hibernateSearchService.initializeHibernateSearch(entityManager);
        return hibernateSearchService;
    }*/

    @Bean(name = "transactionManager")
    @Primary
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager hibernateTransactionManager =
                new HibernateTransactionManager(entityManagerFactory.unwrap(SessionFactory.class));
        return hibernateTransactionManager;
    }

    @Bean(name = "hibernateTemplate")
    public HibernateTemplate getHibernateTemplate() {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(entityManagerFactory.unwrap(SessionFactory.class));
        return hibernateTemplate;
    }

    @Bean("httpLoggingInterceptor")
    public List<HttpLoggingInterceptor> httpLoggingInterceptor() {
        List<HttpLoggingInterceptor> httpLoggingInterceptors = new ArrayList<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpLoggingInterceptors.add(interceptor);
        return httpLoggingInterceptors;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}