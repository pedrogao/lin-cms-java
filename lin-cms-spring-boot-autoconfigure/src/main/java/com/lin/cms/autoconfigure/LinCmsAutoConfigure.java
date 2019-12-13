package com.lin.cms.autoconfigure;

import com.lin.cms.interceptor.AuthorizeInterceptor;
import com.lin.cms.interceptor.LogInterceptor;
import com.lin.cms.beans.RouteMetaCollector;
import com.lin.cms.token.DoubleJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order
@EnableConfigurationProperties(LinCmsProperties.class) // 很重要，插件的配置必须挂载到此
public class LinCmsAutoConfigure {

    @Autowired
    private LinCmsProperties properties;


    /**
     * 记录每个被 @RouteMeta 记录的信息，在beans的后置调用
     *
     * @return RouteMetaCollector
     */
    @Bean
    public RouteMetaCollector postProcessBeans() {
        return new RouteMetaCollector();
    }

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
        return new AuthorizeInterceptor();
    }

    @Bean
    @ConditionalOnProperty(prefix = "lin.cms", value = "logger-enabled", havingValue = "true")
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

}
