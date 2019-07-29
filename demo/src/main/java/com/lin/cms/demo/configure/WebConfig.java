package com.lin.cms.demo.configure;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lin.cms.demo.interceptor.RequestLogInterceptor;
import com.lin.cms.exception.ExceptionHandler;
import com.lin.cms.interceptor.AuthInterceptor;
import com.lin.cms.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
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
    private AuthInterceptor authInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private RequestLogInterceptor requestLogInterceptor;

    @Value("${lin.cms.file.store-dir}")
    private String dir;

    // 使用阿里 FastJson 作为JSON MessageConverter
    // 替换json序列化
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //驼峰转下划线
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);

        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
        config.setSerializeConfig(serializeConfig);

        //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
        //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
        // 按需配置，更多参考FastJson文档哈

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        // 添加至首位，否则会被jackjson覆盖
        converters.add(0, converter);
    }


    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(exceptionHandler);
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    // 添加拦截器
    // 权限拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authEnabled) {
            //开发环境忽略签名认证
            registry.addInterceptor(authInterceptor).excludePathPatterns("/assets/**");
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
        registry.addResourceHandler("/assets/**").addResourceLocations("file:" + absDir + "/");
    }

    private boolean isAbsolute(String str) {
        Path path = FileSystems.getDefault().getPath(str);
        return path.isAbsolute();
    }
}
