package com.renault.pizzaauthserver.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EncryptablePropertySource("classpath:dev.yml")
@Profile("dev")
@Configuration
public class DevPropertiesLoad {
}
