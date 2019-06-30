package com.lin.cms.interceptor;

import com.lin.cms.interfaces.AuthVerifyResolver;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.enums.UserLevel;
import com.lin.cms.core.result.ErrCode;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
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
        if (authInterceptorResolver == null) {
            // 如果没有实现 AuthVerifyResolver 接口的类，则规定一切为true
            return true;
        }
        Result result = ResultGenerator.genResult(ErrCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RouteMeta meta = method.getAnnotation(RouteMeta.class);

            // 如果视图函数没有 RouteMeta 注解，那么视图函数没有被加入到权限控制中
            // 因此，即使被 LoginRequired 等注解装饰，也不会被权限校验
            if (meta == null) {
                return true;
            }

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
        } else {
            ResultGenerator.writeResult(response, result);
            return false;
        }
    }
}
