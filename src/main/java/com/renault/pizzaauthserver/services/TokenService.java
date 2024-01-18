package com.renault.pizzaauthserver.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TokenService {
    Map<String, String> generateToken(User user, HttpServletRequest request);
    UsernamePasswordAuthenticationToken validateAndGetAuthToken(String token);

}
