package com.renault.pizzaauthserver.api.v1.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
