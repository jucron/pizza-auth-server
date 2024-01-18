package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.UserDTO;
import com.renault.pizzaauthserver.domain.Role;
import com.renault.pizzaauthserver.domain.User;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserDTO getUser (String username);
    List<UserDTO> getUsers();

    User getTrueUser(String username);
}
