package com.lin.cms.interceptor;

import com.lin.cms.interfaces.AuthorizeVerifyResolver;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.enums.UserLevel;
import com.lin.cms.core.utils.AnnotationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthorizeVerifyResolver authorizeVerifyResolver;

    @Value("${lin.cms.excludeMethods}")
    private String[] excludeMethods;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkInExclude(request.getMethod())) {
            // 有些请求方法无需检测，如OPTIONS
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RouteMeta meta = method.getAnnotation(RouteMeta.class);
            // 考虑两种情况，1. 有 meta；2. 无 meta
            if (meta == null) {
                // 无meta的话，在权限的范围之外不做拦截
                return true;
            } else {
                // 有meta在权限范围之内，需要判断权限
                return this.handleMeta(request, response, method, meta);
            }
        } else {
            // handler不是HandlerMethod的情况
            return authorizeVerifyResolver.handleNotHandlerMethod(request, response, handler);
        }
    }

    private boolean handleMeta(HttpServletRequest request, HttpServletResponse response, Method method, RouteMeta meta) {
        // 没有挂载到权限系统中，通过
        if (!meta.mount()) {
            return true;
        }
        Annotation[] annotations = method.getAnnotations();
        UserLevel level = AnnotationUtil.findRequired(annotations);
        switch (level) {
            case LOGIN:
                // 登陆权限
                return authorizeVerifyResolver.handleLogin(request, response, meta);
            case GROUP:
                // 分组权限
                return authorizeVerifyResolver.handleGroup(request, response, meta);
            case ADMIN:
                // 管理员权限
                return authorizeVerifyResolver.handleAdmin(request, response, meta);
            case REFRESH:
                // 刷新令牌
                return authorizeVerifyResolver.handleRefresh(request, response, meta);
            default:
                return true;
        }
    }

    private boolean checkInExclude(String method) {
        for (int i = 0; i < excludeMethods.length; i++) {
            if (method.equals(excludeMethods[i])) {
                return true;
            }
        }
        return false;
    }
}
