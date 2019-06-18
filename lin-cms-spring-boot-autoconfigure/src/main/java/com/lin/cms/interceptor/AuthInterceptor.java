package com.lin.cms.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.enums.UserLevel;
import com.lin.cms.core.exception.AuthFailed;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.result.ErrCode;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.interfaces.BaseAuthMapper;
import com.lin.cms.interfaces.BaseUser;
import com.lin.cms.interfaces.BaseUserMapper;
import com.lin.cms.token.JWT;
import com.lin.cms.core.utils.AnnotationHelper;
import com.lin.cms.utils.LocalUser;
import com.lin.cms.model.BaseAuthModel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CollectMetaPostBeanProcessor postProcessor;

    @Autowired
    private JWT jwt;

    private BaseUserMapper userMapper;

    private BaseAuthMapper authMapper;

    public AuthInterceptor(BaseUserMapper userMapper, BaseAuthMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
                        boolean valid = verifyLogin(request, response, result);
                        return valid;
                    case GROUP:
                        boolean valid1 = verifyGroup(request, response, result, meta);
                        return valid1;
                    case ADMIN:
                        boolean valid2 = verifyAdmin(request, response, result);
                        return valid2;
                    case REFRESH:
                        boolean valid3 = verifyRefresh(request, response, result);
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

    private boolean verifyLogin(HttpServletRequest request, HttpServletResponse response, Result result) {
        String tokenStr = this.verifyTokenInHeader(request, response, result);
        // 如果返回的token字符串为空，则表示基础头部认证失败
        if (tokenStr == null) {
            return false;
        }
        Map<String, Claim> claims = jwt.verifyAccess(tokenStr);
        if (claims == null) {
            AuthFailed failed = new AuthFailed("令牌损坏，请重新申请正确的令牌");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        Integer identity = claims.get("identity").asInt();
        String type = claims.get("type").asString();
        String scope = claims.get("scope").asString();

        boolean verifyLinScopeAndAccess = this.verifyLinScopeAndAccess(response, scope, type);
        if (!verifyLinScopeAndAccess) {
            return false;
        }
        BaseUser user = userMapper.selectByPrimaryKey(identity);
        if (user == null) {
            // TODO: 尝试抛异常
            NotFound notFound = new NotFound("用户不存在");
            ResultGenerator.genResult(notFound);
            return false;
        }
        LocalUser.setLocalUser(user);
        return true;
    }

    private boolean verifyGroup(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        boolean stepValid = this.verifyLogin(request, response, result);
        if (!stepValid) {
            return false;
        }
        BaseUser user = LocalUser.getLocalUser();
        if (user.ifIsAdmin()) {
            return true;
        }
        Integer groupId = user.getGroupId();
        if (groupId == null) {
            // 您还不属于任何权限组，请联系超级管理员获得权限
            AuthFailed failed = new AuthFailed("您还不属于任何权限组，请联系超级管理员获得权限");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        BaseAuthModel auth = authMapper.selectOneByGroupIdAndAuthAndModule(groupId, meta.auth(), meta.module());
        if (auth == null) {
            // 权限不够，请联系超级管理员获得权限
            AuthFailed failed = new AuthFailed("权限不够，请联系超级管理员获得权限");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        return true;
    }

    private boolean verifyAdmin(HttpServletRequest request, HttpServletResponse response, Result result) {
        boolean stepValid = this.verifyLogin(request, response, result);
        if (!stepValid) {
            return stepValid;
        }
        BaseUser user = LocalUser.getLocalUser();
        return user.ifIsAdmin();
    }

    private boolean verifyLinScopeAndAccess(HttpServletResponse response, String scope, String type) {
        // 先判断scope，scope不对直接false
        if (!scope.equals(JWT.LIN_SCOPE)) {
            AuthFailed failed = new AuthFailed("您的令牌领域(scope)错误");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        // 先判断token类型，login校验必须为access
        if (!type.equals(JWT.ACCESS_TYPE)) {
            AuthFailed failed = new AuthFailed("您的令牌类型错误");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        return true;
    }


    private String verifyTokenInHeader(HttpServletRequest request, HttpServletResponse response, Result result) {
        // 处理头部header,带有access_token的可以访问
        String authorization = request.getHeader("Authorization");
        if (authorization == null || Strings.isBlank(authorization)) {
            result.setMsg("请输入正确的认证头信息");
            ResultGenerator.writeResult(response, result);
            return null;
        }
        String[] splits = authorization.split(" ");
        if (splits.length != 2) {
            result.setMsg("请输入正确的认证头信息");
            ResultGenerator.writeResult(response, result);
            return null;
        } else {
            // Bearer 字段
            String scheme = splits[0];
            // token 字段
            String tokenStr = splits[1];
            String pattern = "^Bearer$";
            if (Pattern.matches(pattern, scheme)) {
                return tokenStr;
            }
        }
        result.setMsg("请输入正确的认证头信息");
        ResultGenerator.writeResult(response, result);
        return null;
    }

    private boolean verifyRefresh(HttpServletRequest request, HttpServletResponse response, Result result) {
        String tokenStr = this.verifyTokenInHeader(request, response, result);
        if (tokenStr == null) {
            return false;
        }
        Map<String, Claim> claims = jwt.verifyRefresh(tokenStr);

        Integer identity = claims.get("identity").asInt();
        String type = claims.get("type").asString();
        String scope = claims.get("scope").asString();
        // 先判断scope，scope不对直接false
        if (!scope.equals(JWT.LIN_SCOPE)) {
            AuthFailed failed = new AuthFailed("您的令牌领域(scope)错误");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        // 先判断token类型，login校验必须为access
        if (!type.equals(JWT.REFRESH_TYPE)) {
            AuthFailed failed = new AuthFailed("您的令牌类型错误");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        BaseUser user = userMapper.selectByPrimaryKey(identity);
        if (user == null) {
            NotFound notFound = new NotFound("用户不存在");
            ResultGenerator.genAndWriteResult(response, notFound);
            return false;
        }
        LocalUser.setLocalUser(user);
        return true;
    }
}
