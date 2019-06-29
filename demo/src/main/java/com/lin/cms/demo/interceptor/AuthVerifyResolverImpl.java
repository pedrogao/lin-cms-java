package com.lin.cms.demo.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lin.cms.base.mapper.AuthMapper;
import com.lin.cms.base.mapper.GroupMapper;
import com.lin.cms.base.mapper.UserMapper;
import com.lin.cms.base.model.AuthDO;
import com.lin.cms.base.model.UserDO;
import com.lin.cms.base.utils.LocalUser;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.exception.AuthFailed;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.interfaces.AuthVerifyResolver;
import com.lin.cms.token.JWT;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class AuthVerifyResolverImpl implements AuthVerifyResolver {


    @Autowired
    private JWT jwt;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private AuthMapper authMapper;


    public boolean verifyLogin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
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
        UserDO user = userMapper.selectByPrimaryKey(identity);
        if (user == null) {
            NotFound notFound = new NotFound("用户不存在");
            ResultGenerator.genResult(notFound);
            return false;
        }
        LocalUser.setLocalUser(user);
        return true;
    }

    public boolean verifyGroup(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        boolean stepValid = this.verifyLogin(request, response, result, meta);
        if (!stepValid) {
            return false;
        }
        UserDO user = LocalUser.getLocalUser();
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
        AuthDO auth = authMapper.selectOneByGroupIdAndAuthAndModule(groupId, meta.auth(), meta.module());
        if (auth == null) {
            // 权限不够，请联系超级管理员获得权限
            AuthFailed failed = new AuthFailed("权限不够，请联系超级管理员获得权限");
            ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        return true;
    }

    public boolean verifyAdmin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        boolean stepValid = this.verifyLogin(request, response, result, meta);
        if (!stepValid) {
            return stepValid;
        }
        UserDO user = LocalUser.getLocalUser();
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

    public boolean verifyRefresh(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
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
        UserDO user = userMapper.selectByPrimaryKey(identity);
        if (user == null) {
            NotFound notFound = new NotFound("用户不存在");
            ResultGenerator.genAndWriteResult(response, notFound);
            return false;
        }
        LocalUser.setLocalUser(user);
        return true;
    }
}
