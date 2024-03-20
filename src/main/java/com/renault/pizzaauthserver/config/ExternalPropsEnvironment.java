package com.renault.pizzaauthserver.config;

import com.renault.pizzaauthserver.domain.TokenProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
@Configuration
public class ExternalPropsEnvironment {

    private final Environment env;

    @Bean
    public TokenProperties tokenProperties() {
        return TokenProperties.builder()
                .SECRET_KEY(env.getProperty("secret-key"))
                .tokenExpirationTime(Integer.parseInt(
                        Objects.requireNonNull(
                                env.getProperty("token.time.expiration"))))
                .build();
    }
}
