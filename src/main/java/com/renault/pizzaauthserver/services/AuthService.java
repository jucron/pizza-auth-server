package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.AuthRegisterDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRequestDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(AuthRegisterDTO register);
    AuthResponseDTO authenticate(AuthRequestDTO authentication);
}
