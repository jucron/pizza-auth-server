package com.renault.pizzaauthserver.controllers.v1;

import com.renault.pizzaauthserver.api.v1.model.AuthRegisterDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRequestDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthResponseDTO;
import com.renault.pizzaauthserver.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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

}
