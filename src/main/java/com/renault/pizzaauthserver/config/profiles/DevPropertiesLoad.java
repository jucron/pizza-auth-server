package com.renault.pizzaauthserver.config.profiles;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EncryptablePropertySource("classpath:dev-properties.yml")
@Profile("dev")
@Configuration
public class DevPropertiesLoad {
}
