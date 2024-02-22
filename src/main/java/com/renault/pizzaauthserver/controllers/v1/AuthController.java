package com.renault.pizzaauthserver.controllers.v1;

import com.renault.pizzaauthserver.api.v1.model.*;
import com.renault.pizzaauthserver.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody AuthRegisterDTO authRegisterDTO) {
        return authService.register(authRegisterDTO);
    }

    @PostMapping("/auth")
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.authenticate(authRequestDTO);
    }

    @PostMapping("/refresh")
    public AuthResponseDTO refresh() {
        return authService.refresh();
    }

    @PostMapping("/introspect")
    public AuthIntrospectResponseDTO introspect(@RequestBody AuthIntrospectRequestDTO introspectRequest) {
        return authService.introspect(introspectRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(getTokenFromHeader(authorizationHeader));
    }
    private String getTokenFromHeader(String header) {
        return header.substring("Bearer ".length());
    }
}
