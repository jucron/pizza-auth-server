package com.renault.pizzaauthserver.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String jwtToken);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    //2 Token builders with 24hours of lifeCycle, one of them just have a subject
    String generateToken (Map<String, Object> extraClaims, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    void addTokenToBlackList(String token);
}
