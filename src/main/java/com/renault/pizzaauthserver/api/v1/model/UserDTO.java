package com.renault.pizzaauthserver.api.v1.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserDTO {
    private String name;
    private String username;
    private String password;
}
