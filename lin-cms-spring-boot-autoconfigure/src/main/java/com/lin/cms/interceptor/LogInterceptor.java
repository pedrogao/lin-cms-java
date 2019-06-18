package com.lin.cms.interceptor;

import com.lin.cms.core.annotation.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 记录日志
        // TODO: 从handler中拿到Logger注解中的信息，解析request，response , modelAndView 记录相关的日志
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Logger logger = method.getAnnotation(Logger.class);
            if (logger != null) {
                // parse template and extract properties from request,response and modelAndView
                String template = logger.template();
                // BaseUserModel user = LocalUser.getLocalUser();
                log.info(template);
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
