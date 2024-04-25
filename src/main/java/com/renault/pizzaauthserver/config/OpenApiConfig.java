package com.renault.pizzaauthserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "João Renault",
                        email = "jucron@gmail.com"
                ),
                title = "Pizza Auth Server",
                description = "The authorization server of a Pizza App",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "LOCAL_DEV_ENVIRONMENT",
                        url = "http://localhost:8082/"
                )
        }
//        security =
)
@SecurityScheme(
        name = OpenApiConfig.SECURITY_SCHEME_NAME,
        description = "Provide a JWT to try authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_NAME = "BearerAuth";
}
