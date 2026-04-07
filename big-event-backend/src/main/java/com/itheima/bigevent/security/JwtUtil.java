package com.itheima.bigevent.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static String secretKey;
    private static long expiration;

    @Value("${jwt.secret:itheima}")
    public void setSecretKey(String key) {
        JwtUtil.secretKey = key;
    }

    @Value("${jwt.expiration:43200000}")
    public void setExpiration(long exp) {
        JwtUtil.expiration = exp;
    }

    /**
     * 生成 JWT Token
     */
    public static String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 从 Token 中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getSubject();
    }

    /**
     * 验证 Token 是否有效
     */
    public static boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
