package com.renault.pizzaauthserver.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthIntrospectRequestDTO {
    private String requestedPermission;
    private String usernameImpacted;
}
