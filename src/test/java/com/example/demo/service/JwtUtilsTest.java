package com.example.demo.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.List;

class JwtUtilsTest {
    private static JwtUtils jwtUtils = new JwtUtils();

    @Test
    public void testGenerate() {
        String generatedToken = jwtUtils.generate("user", List.of());
        System.out.println(generatedToken);

        Claims claimByToken = jwtUtils.getClaims(generatedToken);
        System.out.println(claimByToken);
    }
}