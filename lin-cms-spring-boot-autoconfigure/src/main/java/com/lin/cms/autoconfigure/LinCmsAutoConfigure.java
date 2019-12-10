package com.lin.cms.autoconfigure;

import com.lin.cms.beans.ExceptionResultImpl;
import com.lin.cms.beans.Manager;
import com.lin.cms.exception.ExceptionHandler;
import com.lin.cms.interceptor.AuthorizeInterceptor;
import com.lin.cms.interceptor.LogInterceptor;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.interfaces.ExceptionResultResolver;
import com.lin.cms.token.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnClass(Manager.class)
@ConditionalOnBean(Manager.class)
@Order
@EnableConfigurationProperties(LinCmsProperties.class) // 很重要，插件的配置必须挂载到此
public class LinCmsAutoConfigure {

    @Autowired
    private LinCmsProperties properties;


    /**
     * 记录每个被 @RouteMeta 记录的信息，在beans的后置调用
     *
     * @return CollectMetaPostBeanProcessor
     */
    @Bean
    public CollectMetaPostBeanProcessor postProcessBeans() {
        return new CollectMetaPostBeanProcessor();
    }

    @Bean
    public JWT jwter() {
        String secret = properties.getTokenSecret();
        Long accessExpire = properties.getTokenAccessExpire();
        Long refreshExpire = properties.getTokenRefreshExpire();
        if (accessExpire == null) {
            accessExpire = 60 * 60L;
        }
        if (refreshExpire == null) {
            refreshExpire = 60 * 60 * 24 * 30L;
        }
        return new JWT(secret, accessExpire, refreshExpire);
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionResultResolver.class)
    public ExceptionResultImpl exceptionResult() {
        return new ExceptionResultImpl();
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

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }

}
