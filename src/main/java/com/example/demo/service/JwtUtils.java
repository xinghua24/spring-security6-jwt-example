package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.joining;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final long expire = 60 * 60 * 1000; /* 1h */

    SecretKey secretKey = Keys.hmacShaKeyFor("a very long secret a very long secret".getBytes());
    // Alternative: to use random secret key
    // SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generate(String username,
                           Collection<? extends GrantedAuthority> authorities) {
        Date nowDate = new Date();

        Date expireDate = new Date(nowDate.getTime() + expire);

        ClaimsBuilder claimsBuilder = Jwts.claims();
        if (authorities != null && !authorities.isEmpty()) {
            claimsBuilder.add("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        }
        return Jwts.builder()
            .claims(claimsBuilder.build())
            .subject(username)
            .issuedAt(nowDate)
            .expiration(expireDate)
            .signWith(secretKey)
            .compact();
    }

    public boolean validate(String token) {
        return getUsername(token) != null && !isExpired(token);
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public String getRoles(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("roles");
    }

    public Claims getClaims(String jws) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jws).getPayload();
        } catch (Exception e) {
            logger.error("validate token error");
            return null;
        }
    }
}