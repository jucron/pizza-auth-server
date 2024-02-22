package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.*;

public interface AuthService {
    AuthResponseDTO register(AuthRegisterDTO register);
    AuthResponseDTO authenticate(AuthRequestDTO authentication);
    AuthIntrospectResponseDTO introspect(AuthIntrospectRequestDTO introspectRequest);

    AuthResponseDTO refresh();

    void logout(String token);
}
