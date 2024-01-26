package com.renault.pizzaauthserver.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
