package com.lin.cms.token;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWT {
    public static String ACCESS_TYPE = "access";
    public static String REFRESH_TYPE = "refresh";

    public static String LIN_SCOPE = "lin";
    public static String OTHER_SCOPE = "other";

    private String secret;

    private Long accessExpire;

    private Long refreshExpire;

    public JWT(String secret, Long accessExpire, Long refreshExpire) {
        this.secret = secret;
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
    }

    public String generateToken(String tokenType, Long identity, String scope, long expire) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        Date expireDate = this.getExpireDate(expire);
        String token = com.auth0.jwt.JWT.create()
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(expireDate)
                .sign(algorithm);
        return token;
    }

    /***
     * 获得令牌的验证器
     * @param expire 令牌的过期时间
     * @return JWTVerifier
     */
    public JWTVerifier getVerifier(long expire) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .acceptExpiresAt(expire)
                .build();
        return verifier;
    }

    public JWTCreator.Builder getBuilder() {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        return builder;
    }

    public String getSpecifyToken(long expire, Map<String, String> payloads) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        Date expireDate = this.getExpireDate(expire);
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        payloads.forEach(builder::withClaim);
        builder.withExpiresAt(expireDate);
        String token = builder.sign(algorithm);
        return token;
    }

    public Map<String, Claim> verifyAccess(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .acceptExpiresAt(this.accessExpire) // 一个小时 单位 s
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        return claims;
    }


    public Map<String, Claim> verifyRefresh(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .acceptExpiresAt(this.refreshExpire) // 一个月
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        return claims;
    }

    public String generateAccessToken(Long identity) {
        return this.generateToken(JWT.ACCESS_TYPE, identity, LIN_SCOPE, this.accessExpire);
    }

    public String generateRefreshToken(Long identity) {
        return this.generateToken(JWT.REFRESH_TYPE, identity, LIN_SCOPE, this.refreshExpire);
    }

    public Map generateTokens(Long identity) {
        Map tokens = new HashMap<>();
        String access = this.generateToken(JWT.ACCESS_TYPE, identity, LIN_SCOPE, this.accessExpire);
        String refresh = this.generateToken(JWT.REFRESH_TYPE, identity, LIN_SCOPE, this.refreshExpire);
        tokens.put("access_token", access);
        tokens.put("refresh_token", refresh);
        return tokens;
    }

    /**
     * 获得过期时间
     *
     * @param expire 过期时间
     * @return Date
     */
    private Date getExpireDate(long expire) {
        long nowTime = new Date().getTime();
        long expireTime = nowTime + expire;
        Date expireDate = new Date(expireTime);
        return expireDate;
    }

    /**
     * 获得默认的token加密方法
     *
     * @return Algorithm
     */
    public Algorithm getDefaultAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        return algorithm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getAccessExpire() {
        return accessExpire;
    }

    public void setAccessExpire(Long accessExpire) {
        this.accessExpire = accessExpire;
    }

    public Long getRefreshExpire() {
        return refreshExpire;
    }

    public void setRefreshExpire(Long refreshExpire) {
        this.refreshExpire = refreshExpire;
    }
}
