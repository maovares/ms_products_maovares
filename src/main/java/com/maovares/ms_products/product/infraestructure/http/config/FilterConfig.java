package com.maovares.ms_products.product.infraestructure.http.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maovares.ms_products.product.infraestructure.http.filter.ClientCertValidationFilter;
import com.maovares.ms_products.product.infraestructure.http.filter.CorrelationIdFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> correlationIdFilter() {
        FilterRegistrationBean<CorrelationIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorrelationIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setName("correlationIdFilter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ClientCertValidationFilter> clientCertValidationFilter() {
        FilterRegistrationBean<ClientCertValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ClientCertValidationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        registrationBean.setName("clientCertValidationFilter");
        return registrationBean;
    }
}
