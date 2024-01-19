package com.renault.pizzaauthserver.bootstrap;

import com.renault.pizzaauthserver.api.v1.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.renault.pizzaauthserver.domain.Role;
import com.renault.pizzaauthserver.domain.RoleList;
//import com.renault.pizzaauthserver.services.UserService;
/*
@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private UserService userService;

    public Bootstrap(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        populateUsersData();


        log.info("\n- Data Loaded in Categories = " + userService.getUsers().size());
    }

    private void populateUsersData() {

        //Creating different roles

        userService.saveRole(new Role(null, "ROLE_ADMIN", RoleList.ADMIN.getPermissions()));
        userService.saveRole(new Role(null,"ROLE_MANAGER", RoleList.MANAGER.getPermissions()));
        userService.saveRole(new Role(null,"ROLE_USER", RoleList.USER.getPermissions()));


        UserDTO user1DTO = new UserDTO(); user1DTO.setName("John Travolta");
        user1DTO.setUsername("john"); user1DTO.setPassword("1234");
        userService.saveUser(user1DTO);

        UserDTO user2DTO = new UserDTO(); user2DTO.setName("Will Smith");
        user2DTO.setUsername("will"); user2DTO.setPassword("1234");
        userService.saveUser(user2DTO);

        UserDTO user3DTO = new UserDTO(); user3DTO.setName("Jim Carrey");
        user3DTO.setUsername("jim"); user3DTO.setPassword("1234");
        userService.saveUser(user3DTO);

        UserDTO user4DTO = new UserDTO(); user4DTO.setName("Arnold Schwarzenegger");
        user4DTO.setUsername("arnold"); user4DTO.setPassword("1234");
        userService.saveUser(user4DTO);

        userService.addRoleToUser("john", "ROLE_ADMIN");
        userService.addRoleToUser("will", "ROLE_MANAGER");
        userService.addRoleToUser("jim", "ROLE_USER");
        userService.addRoleToUser("arnold", "ROLE_USER");

    }
}


 */