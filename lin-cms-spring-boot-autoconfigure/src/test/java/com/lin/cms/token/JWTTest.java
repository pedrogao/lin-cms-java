package com.lin.cms.token;

import static org.junit.Assert.*;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTTest {

    private JWT jwt = new JWT("secret", 1000L, 1000L);

    private String token;

    @Test
    public void generateToken() {
    }


    @Test
    public void getVerifier() {
        Map<String, String> payloads = new HashMap();
        payloads.put("identity", "pedro");
        payloads.put("scope", "334");
        payloads.put("ip", "129.898.11.33");
        String token = jwt.getSpecifyToken(jwt.getAccessExpire(), payloads);
        log.info(token);
        JWTVerifier verifier = jwt.getVerifier(jwt.getAccessExpire());
        DecodedJWT decodedJWT = verifier.verify(token);
        String identity = decodedJWT.getClaim("identity").asString();
        String scope = decodedJWT.getClaim("scope").asString();
        assertEquals(identity, "pedro");
        assertEquals(scope, "334");
    }


    @Test
    public void getBuilder() {
    }


    @Test
    public void getSpecifyToken() {
        Map<String, String> payloads = new HashMap();
        payloads.put("identity", "pedro");
        payloads.put("scope", "334");
        payloads.put("ip", "129.898.11.33");
        String token = jwt.getSpecifyToken(jwt.getAccessExpire(), payloads);
        log.info(token);
        this.token = token;
        assertNotNull(token);
    }


    @Test
    public void verifyAccess() {
    }


    @Test
    public void verifyRefresh() {
    }


    @Test
    public void generateAccessToken() {
    }


    @Test
    public void generateRefreshToken() {
    }


    @Test
    public void generateTokens() {
    }


    @Test
    public void getDefaultAlgorithm() {
    }
}