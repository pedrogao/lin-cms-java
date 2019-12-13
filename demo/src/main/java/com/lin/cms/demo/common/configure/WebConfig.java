package com.lin.cms.demo.common.configure;

import com.lin.cms.demo.common.interceptor.RequestLogInterceptor;
import com.lin.cms.interceptor.AuthorizeInterceptor;
import com.lin.cms.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${auth.enabled}")
    private boolean authEnabled;

    @Value("${request-log.enabled}")
    private boolean requestLogEnabled;

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private RequestLogInterceptor requestLogInterceptor;

    @Value("${lin.cms.file.store-dir}")
    private String dir;

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    }

    /**
     * 跨域
     * 注意： 跨域问题涉及安全性问题，这里提供的是最方便简单的配置，任何host和任何方法都可跨域
     * 但在实际场景中，这样做，无疑很危险，所以谨慎选择开启或者关闭
     * 如果切实需要，请咨询相关安全人员或者专家进行配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    // 添加拦截器
    // 权限拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authEnabled) {
            //开发环境忽略签名认证
            registry.addInterceptor(authorizeInterceptor).excludePathPatterns("/assets/**");
        }
        if (requestLogEnabled) {
            registry.addInterceptor(requestLogInterceptor);
        }
        registry.addInterceptor(logInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absDir;
        if (this.isAbsolute(this.dir)) {
            absDir = this.dir;
        } else {
            String cmd = System.getProperty("user.dir");
            Path path = FileSystems.getDefault().getPath(cmd, this.dir);
            absDir = path.toAbsolutePath().toString();
        }
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:" + absDir + "/");
    }

    private boolean isAbsolute(String str) {
        Path path = FileSystems.getDefault().getPath(str);
        return path.isAbsolute();
    }
}
