package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.AuthRegisterDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRequestDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
//todo
    public AuthResponseDTO register(AuthRegisterDTO authRegisterDTO) {
        return null;
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        return null;
    }
}
