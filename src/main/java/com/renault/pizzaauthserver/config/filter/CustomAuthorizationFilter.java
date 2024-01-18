package com.renault.pizzaauthserver.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renault.pizzaauthserver.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.renault.pizzaauthserver.config.SecurityConfig.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override //This intercepts any requests and customize authorizations
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // We need to clear the path for the standard login first
        if (request.getServletPath().equals(OPEN_ENDPOINT_A) ||
                request.getServletPath().equals(OPEN_ENDPOINT_B)) {
            filterChain.doFilter(request, response); //this let the request goes through
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { //This must be expected and implemented in the frontend
                try {

                    String token = authorizationHeader.substring("Bearer ".length());

                    UsernamePasswordAuthenticationToken authenticationToken = tokenService.validateAndGetAuthToken(token);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); //Set this user in security context Holder
                    filterChain.doFilter(request, response);

                } catch (Exception exception) {
                    log.error("Error login in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
//                    response.sendError(FORBIDDEN.value()); //Option 1: send a http status error back
                    //Option 2: Send a JSON with the error embedded
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response); //just let the request continue
            }
        }
    }
}

