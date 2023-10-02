package com.microservice.identityservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
@Component
public class JwtUtil {
    @Value("${data.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.data.app.jwtExpirationMs}")
    private long jwtExpirationMs;


    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return JWT.create().withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withIssuer(user.getUsername())
                .withExpiresAt(Instant.ofEpochMilli(System.currentTimeMillis()+jwtExpirationMs))
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public void verifyJwtToken(String token){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String username = decodedJWT.getSubject();
        // System.out.println("Role"+role);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
