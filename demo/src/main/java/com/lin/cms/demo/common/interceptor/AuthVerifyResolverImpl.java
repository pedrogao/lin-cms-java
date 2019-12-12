package com.lin.cms.demo.common.interceptor;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.exception.*;
import com.lin.cms.response.Result;
import com.lin.cms.exception.TokenExpiredException;
import com.lin.cms.utils.ResultGenerator;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.common.LocalUserLegacy;
import com.lin.cms.interfaces.AuthVerifyResolver;
import com.lin.cms.token.DoubleJWT;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

import static com.lin.cms.consts.TokenConst.*;

@SuppressWarnings("Duplicates")
@Component
public class AuthVerifyResolverImpl implements AuthVerifyResolver {


    @Autowired
    private DoubleJWT jwt;

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
        Map<String, Claim> claims = null;
        try {
            claims = jwt.decodeAccessToken(tokenStr);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            TokenExpiredException expired = new TokenExpiredException("令牌过期，请重新申请令牌");
            // ResultGenerator.genAndWriteResult(response, expired);
            return false;
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            TokenInvalidException invalid = new TokenInvalidException("令牌损坏，请检查令牌");
            // ResultGenerator.genAndWriteResult(response, invalid);
            return false;
        }
        if (claims == null) {
            TokenInvalidException invalid = new TokenInvalidException("令牌损坏，解析错误，请重新申请正确的令牌");
            // ResultGenerator.genAndWriteResult(response, invalid);
            return false;
        }
        // 先判断 scope 作用域
        String scope = claims.get("scope").asString();
        // 为 lin 作用域下的令牌校验
        if (scope.equals(LIN_SCOPE)) {
            Integer identity = claims.get("identity").asInt();
            String type = claims.get("type").asString();

            boolean verifyLinAccess = this.verifyLinAccess(response, type);
            if (!verifyLinAccess) {
                return false;
            }
            UserDO user = userMapper.selectById(identity);
            if (user == null) {
                NotFoundException notFoundException = new NotFoundException("用户不存在");
                ResultGenerator.generateResult(notFoundException);
                return false;
            }
            LocalUserLegacy.setLocalUser(user);
            return true;
        } else {
            // 其它作用域 暂时返回 false，即其它作用域下均校验失败
            TokenInvalidException invalid = new TokenInvalidException("您的令牌领域(scope)错误");
            // ResultGenerator.genAndWriteResult(response, invalid);
            return false;
        }
    }

    public boolean verifyGroup(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        boolean stepValid = this.verifyLogin(request, response, result, meta);
        if (!stepValid) {
            return false;
        }
        UserDO user = LocalUserLegacy.getLocalUser();
        if (user.checkAdmin()) {
            return true;
        }
        Long groupId = user.getGroupId();
        if (groupId == null) {
            // 您还不属于任何权限组，请联系超级管理员获得权限
            AuthenticationException exception = new AuthenticationException("您还不属于任何权限组，请联系超级管理员获得权限");
            // ResultGenerator.genAndWriteResult(response, exception);
            return false;
        }
        AuthDO auth = authMapper.selectOneByGroupIdAndAuthAndModule(groupId, meta.permission(), meta.module());
        if (auth == null) {
            // 权限不够，请联系超级管理员获得权限
            AuthenticationException exception = new AuthenticationException("权限不够，请联系超级管理员获得权限");
            // ResultGenerator.genAndWriteResult(response, exception);
            return false;
        }
        return true;
    }

    public boolean verifyAdmin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        boolean stepValid = this.verifyLogin(request, response, result, meta);
        if (!stepValid) {
            return stepValid;
        }
        UserDO user = LocalUserLegacy.getLocalUser();
        return user.checkAdmin();
    }

    private boolean verifyLinAccess(HttpServletResponse response, String type) {
        // 先判断token类型，login校验必须为access
        if (!type.equals(ACCESS_TYPE)) {
            TokenInvalidException invalid = new TokenInvalidException("您的令牌类型错误");
            // ResultGenerator.genAndWriteResult(response, invalid);
            return false;
        }
        return true;
    }


    private String verifyTokenInHeader(HttpServletRequest request, HttpServletResponse response, Result result) {
        // 处理头部header,带有access_token的可以访问
        String authorization = request.getHeader("Authorization");
        if (authorization == null || Strings.isBlank(authorization)) {
            result.setMsg("请输入正确的认证头信息");
            // ResultGenerator.writeResult(response, result);
            return null;
        }
        String[] splits = authorization.split(" ");
        if (splits.length != 2) {
            result.setMsg("请输入正确的认证头信息");
            // ResultGenerator.writeResult(response, result);
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
        // ResultGenerator.writeResult(response, result);
        return null;
    }

    public boolean verifyRefresh(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        String tokenStr = this.verifyTokenInHeader(request, response, result);
        if (tokenStr == null) {
            return false;
        }
        Map<String, Claim> claims = null;
        try {
            claims = jwt.decodeRefreshToken(tokenStr);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            RefreshFailedException failed = new RefreshFailedException("令牌过期，请重新申请令牌");
            // ResultGenerator.genAndWriteResult(response, failed);
            return false;
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            RefreshFailedException failed = new RefreshFailedException("令牌损坏，请检查令牌");
            // ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        if (claims == null) {
            RefreshFailedException failed = new RefreshFailedException("令牌损坏，请重新申请正确的令牌");
            // ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        String scope = claims.get("scope").asString();
        Integer identity = claims.get("identity").asInt();
        String type = claims.get("type").asString();
        // 先判断scope，scope不对直接false
        if (!scope.equals(LIN_SCOPE)) {
            RefreshFailedException failed = new RefreshFailedException("您的令牌领域(scope)错误");
            // ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        // 先判断token类型，login校验必须为access
        if (!type.equals(REFRESH_TYPE)) {
            RefreshFailedException failed = new RefreshFailedException("您的令牌类型错误");
            // ResultGenerator.genAndWriteResult(response, failed);
            return false;
        }
        UserDO user = userMapper.selectById(identity);
        if (user == null) {
            NotFoundException notFoundException = new NotFoundException("用户不存在");
            // ResultGenerator.genAndWriteResult(response, notFoundException);
            return false;
        }
        LocalUserLegacy.setLocalUser(user);
        return true;
    }
}
