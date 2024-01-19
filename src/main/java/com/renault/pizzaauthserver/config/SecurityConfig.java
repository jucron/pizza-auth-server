package com.renault.pizzaauthserver.config;

import com.renault.pizzaauthserver.domain.Permission;
import com.renault.pizzaauthserver.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
/*
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String OPEN_ENDPOINT_A = "/api/login";
    public static final String OPEN_ENDPOINT_B = "/api/token/refresh";

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), tokenService);
        customAuthenticationFilter.setFilterProcessesUrl(OPEN_ENDPOINT_A); //This will customize the login path from SpringSecurity

        http.csrf().disable(); //Cross site request forgery deactivation
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //deactivating session

        //Open end-points (For Login and Refresh:
        http.authorizeRequests().antMatchers(
                OPEN_ENDPOINT_A+"/**", OPEN_ENDPOINT_B + "/**")
                .permitAll();

        //GET, PUT, PATCH requests
        http.authorizeRequests().antMatchers(GET, "/api/**").hasAnyAuthority(Permission.READ.permission);
        http.authorizeRequests().antMatchers(PUT, "/api/**").hasAnyAuthority(Permission.UPDATE.permission);
        http.authorizeRequests().antMatchers(PATCH, "/api/**").hasAnyAuthority(Permission.UPDATE.permission);
        //POST and DELETE requests
        http.authorizeRequests().antMatchers(POST, "/api/**").hasAnyAuthority(Permission.WRITE.permission);
        http.authorizeRequests().antMatchers(DELETE, "/api/**").hasAnyAuthority(Permission.DELETE.permission);

        http.authorizeRequests().anyRequest().authenticated(); //This makes all requests to be authenticated
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(tokenService), UsernamePasswordAuthenticationFilter.class); //must be before all others filters
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override //No security webpoints
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/","index",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-ui/**"
                );
    }
}


 */