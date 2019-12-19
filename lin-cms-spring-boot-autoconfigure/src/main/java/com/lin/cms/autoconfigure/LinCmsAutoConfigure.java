package com.lin.cms.autoconfigure;

import com.lin.cms.interceptor.AuthorizeInterceptor;
import com.lin.cms.interceptor.LogInterceptor;
import com.lin.cms.core.token.DoubleJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(LinCmsProperties.class)
public class LinCmsAutoConfigure {

    @Autowired
    private LinCmsProperties properties;

    @Bean
    public DoubleJWT jwter() {
        String secret = properties.getTokenSecret();
        Long accessExpire = properties.getTokenAccessExpire();
        Long refreshExpire = properties.getTokenRefreshExpire();
        if (accessExpire == null) {
            accessExpire = 60 * 60L;
        }
        if (refreshExpire == null) {
            refreshExpire = 60 * 60 * 24 * 30L;
        }
        return new DoubleJWT(secret, accessExpire, refreshExpire);
    }

    @Bean
    public AuthorizeInterceptor authInterceptor() {
        String[] excludeMethods = properties.getExcludeMethods();
        return new AuthorizeInterceptor(excludeMethods);
    }

    @Bean
    @ConditionalOnProperty(prefix = "lin.cms", value = "logger-enabled", havingValue = "true")
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

}
