package com.microservice.apigateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${data.app.jwtSecret}")
    private String jwtSecret;

    public void verifyJwtToken(String token){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String username = decodedJWT.getSubject();
        System.out.println("username"+username);
        // System.out.println("Role"+role);

    }
}
