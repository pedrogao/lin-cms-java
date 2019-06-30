package com.lin.cms.interceptor;

import com.lin.cms.core.annotation.Logger;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.interfaces.LoggerResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoggerResolver loggerResolver;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 记录日志
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Logger logger = method.getAnnotation(Logger.class);
            RouteMeta meta = method.getAnnotation(RouteMeta.class);
            if (logger != null) {
                // parse template and extract properties from request,response and modelAndView
                loggerResolver.handle(meta, logger, request, response);
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
