package com.token.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Date;
@Configuration
public class JWTUtil {
    /**
     * 校验token
     *
     * @param token
     * @param secret 密钥
     */

    public static String verify(String token, String userId, String secret) {

        try {
            Algorithm al = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(al).withClaim("userId", userId).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return "success";
        } catch (TokenExpiredException e) {
            return "tokenException";
        } catch (Exception e) {
            return "error";
        }

    }

    /**
     * 获取token 信息
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("userId").asString();
        } catch (JWTDecodeException d) {
            return "error";
        }

    }

    /**
     * 生成签名
     */

    public static String sign(String userId, String secret, long expTime) {
        Date date = new Date(System.currentTimeMillis() + expTime);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withClaim("userId", userId).withExpiresAt(date).sign(algorithm);

            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }
}
