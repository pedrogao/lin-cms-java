package com.lin.cms.interceptor;

import com.lin.cms.interfaces.AuthVerifyResolver;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.enums.UserLevel;
import com.lin.cms.beans.ErrCode;
import com.lin.cms.core.result.Result;
import com.lin.cms.utils.ResultGenerator;
import com.lin.cms.core.utils.AnnotationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CollectMetaPostBeanProcessor postProcessor;

    @Autowired
    private AuthVerifyResolver authInterceptorResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            // 如果没有实现 AuthVerifyResolver 接口的类，则规定一切为true
            return true;
        }
        Result result = ResultGenerator.genResult(ErrCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RouteMeta meta = method.getAnnotation(RouteMeta.class);
            // 考虑两种情况，1. 有 meta；2. 无 meta
            // 无meta的情况下，可以使用 LoginRequired 和 AdminRequired 修饰
            // 有meta的情况则走以下逻辑
            if (meta == null) {
                return this.handleNoMeta(request, response, result, method);
            } else {
                return this.handleMeta(request, response, result, method, meta);
            }
        } else {
            // 不是视图函数，则当做失败处理
            ResultGenerator.writeResult(response, result);
            return false;
        }
    }

    private boolean handleNoMeta(HttpServletRequest request, HttpServletResponse response, Result result, Method method) {
        Annotation[] annotations = method.getAnnotations();
        UserLevel level = AnnotationHelper.findRequired(annotations);
        switch (level) {
            case TOURIST:
                return true;
            case LOGIN:
                return authInterceptorResolver.verifyLogin(request, response, result, null);
            case ADMIN:
                return authInterceptorResolver.verifyAdmin(request, response, result, null);
            case REFRESH:
                return authInterceptorResolver.verifyRefresh(request, response, result, null);
            default:
                return false;
        }
    }

    private boolean handleMeta(HttpServletRequest request, HttpServletResponse response, Result result, Method method, RouteMeta meta) {
        // 没有挂载到权限系统中，通过
        if (!meta.mount()) {
            return true;
        }
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        Object meta1 = postProcessor.getMetaMap().get(className + "#" + methodName);
        // 如果已经挂载，且二者相同
        if (meta == meta1) {
            Annotation[] annotations = method.getAnnotations();
            UserLevel level = AnnotationHelper.findRequired(annotations);
            switch (level) {
                case TOURIST:
                    // 如果，当前视图函数是游客权限，则直接返回true
                    return true;
                case LOGIN:
                    // 登陆权限
                    // 必须是登陆权限
                    boolean valid = authInterceptorResolver.verifyLogin(request, response, result, meta);
                    return valid;
                case GROUP:
                    boolean valid1 = authInterceptorResolver.verifyGroup(request, response, result, meta);
                    return valid1;
                case ADMIN:
                    boolean valid2 = authInterceptorResolver.verifyAdmin(request, response, result, meta);
                    return valid2;
                case REFRESH:
                    boolean valid3 = authInterceptorResolver.verifyRefresh(request, response, result, meta);
                    return valid3;
                default:
                    ResultGenerator.writeResult(response, result);
                    return false;
            }
        } else {
            ResultGenerator.writeResult(response, result);
            return false;
        }
    }
}
