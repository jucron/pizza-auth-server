package com.renault.pizzaauthserver.config;

import com.renault.pizzaauthserver.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//Will hold all applicationConfig such as Beans
@Configuration
public class ApplicationConfig {
private UserRepo userRepo;
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found"));
            }
        };
    }

}
