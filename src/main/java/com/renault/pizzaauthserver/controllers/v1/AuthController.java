package com.renault.pizzaauthserver.controllers.v1;

import com.renault.pizzaauthserver.api.v1.model.*;
import com.renault.pizzaauthserver.services.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.renault.pizzaauthserver.config.OpenApiConfig.SECURITY_SCHEME_NAME;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization")
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(
        description = "By providing complete User Data, the system will create a new User and provide a JWT back to be used as authorization.",
            summary = "Register a new User into the system"
    )
    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody AuthRegisterDTO authRegisterDTO) {
        return authService.register(authRegisterDTO);
    }

    @Operation(
            description = "By providing User authentication Data, the system will return a JWT back to be used as authorization.",
            summary = "Authorize an existing User into the system"
    )
    @PostMapping("/auth")
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.authenticate(authRequestDTO);
    }

    @Operation(
            description = "By providing a JWT, the system will return a new JWT back to be used as authorization.",
            summary = "Refresh the JWT provided"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @PostMapping("/refresh")
    public AuthResponseDTO refresh() {
        return authService.refresh();
    }

    @Operation(
            description = "By providing a JWT, the system will verify and return a confirmation of the Token validation.",
            summary = "Introspect the User, returning  if the Token is valid"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @PostMapping("/introspect")
    public AuthIntrospectResponseDTO introspect(@RequestBody AuthIntrospectRequestDTO introspectRequest) {
        return authService.introspect(introspectRequest);
    }

    @Operation(
            description = "By providing a JWT, the system will expire this Token and not accept it anymore as a valid authorization.",
            summary = "Logout the User, expiring the Token"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(getTokenFromHeader(authorizationHeader));
    }
    private String getTokenFromHeader(String header) {
        return header.substring("Bearer ".length());
    }
}
