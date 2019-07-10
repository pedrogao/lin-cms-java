package com.lin.cms.demo.configure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lin.cms.beans.Manager;
import com.lin.cms.demo.exception.ExceptionResultImpl;
import com.lin.cms.demo.extensions.file.FileProperties;
import com.lin.cms.demo.interceptor.RequestLogInterceptor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class CommonConfig {

    @Bean
    public Manager manager() {
        return new Manager();
    }

    @Bean
    public ExceptionResultImpl exceptionResult() {
        return new ExceptionResultImpl();
    }

    @Bean
    public RequestLogInterceptor requestLogInterceptor() {
        return new RequestLogInterceptor();
    }


    /**
     * 接口中，自动转换的有：驼峰转换为下划线，空值输出null
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
            jacksonObjectMapperBuilder.failOnUnknownProperties(false);
            jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        };
    }
}
