package com.renault.pizzaauthserver.config.profiles;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EncryptablePropertySource("classpath:prod-properties.yml")
@Profile("prod")
@Configuration
public class ProdPropertiesLoad {
}
