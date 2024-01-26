package com.renault.pizzaauthserver.config;

import com.renault.pizzaauthserver.domain.RoleList;
import com.renault.pizzaauthserver.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        //A: Validate header received in correct format:
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        //Extract jwtToken (after 'Bearer '):
        final String username;
        jwtToken = authHeader.substring(7);
        //Extract Username using a service (usually the username is the Subject of the payload)
        username = jwtService.extractUsername(jwtToken);
        //Check if username exists in Token received and if the system is not authenticated
        boolean isUserAlreadyAuthenticated = (SecurityContextHolder.getContext().getAuthentication() != null);
        if (username != null && !isUserAlreadyAuthenticated) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                //If Valid, a new token must be created to authenticate the System,
                UsernamePasswordAuthenticationToken authToken = getNewAuthToken(userDetails, request);
                //Update SecurityContextHolder with this new authToken
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    private UsernamePasswordAuthenticationToken getNewAuthToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken;
        // no Credentials are necessary for this new token
        Object credentials = null;
        authToken = new UsernamePasswordAuthenticationToken(userDetails,credentials, userDetails.getAuthorities());
        // token details based on request
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

}