package com.lin.cms.token;

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

    public String generateToken(String tokenType, Integer identity, String scope, long expire) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        long nowTime = new Date().getTime();
        long expireTime = nowTime + expire;
        Date expireDate = new Date(expireTime);
        String token = com.auth0.jwt.JWT.create()
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(expireDate)
                .sign(algorithm);
        return token;
    }

    public Map<String, Claim> verifyAccess(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                    .acceptExpiresAt(this.accessExpire) // 一个小时 单位 s
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            return claims;
        } catch (JWTVerificationException exception) {
            log.error("token: ", exception);
            return null;
        }
    }


    public Map<String, Claim> verifyRefresh(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                    .acceptExpiresAt(this.refreshExpire) // 一个月
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            return claims;
        } catch (JWTVerificationException exception) {
            log.error("token: ", exception);
            return null;
        }
    }

    public String generateAccessToken(Integer identity) {
        return this.generateToken(JWT.ACCESS_TYPE, identity, LIN_SCOPE, this.accessExpire);
    }

    public String generateRefreshToken(Integer identity) {
        return this.generateToken(JWT.REFRESH_TYPE, identity, LIN_SCOPE, this.refreshExpire);
    }

    public Map generateTokens(Integer identity) {
        Map tokens = new HashMap<>();
        String access = this.generateToken(JWT.ACCESS_TYPE, identity, LIN_SCOPE, this.accessExpire);
        String refresh = this.generateToken(JWT.REFRESH_TYPE, identity, LIN_SCOPE, this.refreshExpire);
        tokens.put("access_token", access);
        tokens.put("refresh_token", refresh);
        return tokens;
    }
}
