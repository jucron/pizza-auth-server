package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.mapper.UserMapper;
import com.renault.pizzaauthserver.api.v1.model.UserDTO;
import com.renault.pizzaauthserver.domain.Role;
import com.renault.pizzaauthserver.domain.User;
import com.renault.pizzaauthserver.repositories.RoleRepo;
import com.renault.pizzaauthserver.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
/*
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user==null) {
            log.info("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<String> permissions =  user.getRole().getPermissions();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
//        user.getRole().getPermissions().forEach(permission ->
//                        authorities.add(new SimpleGrantedAuthority(permission)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        log.info("Attempting to save new user {} to database", userDTO.getName());
        //Checking username in repo:
        User existingUser = userRepo.findByUsername(userDTO.getUsername());
        if (existingUser!=null) {
            log.info("Username {} already exists in DataBase. User not saved.",userDTO.getUsername());
            return new UserDTO();
        }
        User newUser = userMapper.userDTOToUser(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Note: Role of this User is not yet defined
        userRepo.save(newUser);

        userDTO.setPassword("hidden");
        return userDTO;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        if (role==null) {
            log.info("Role not found, operation cancelled");
        } else {
            user.setRole(role);
            userRepo.save(user);
            log.info("Adding role {} to user {}", roleName, username);
        }
    }

    @Override
    public UserDTO getUser(String username) {
        log.info("Fetching user {}", username);
        UserDTO userDTO = userMapper.userToUserDTO(userRepo.findByUsername(username));
        userDTO.setPassword("hidden");
        return userDTO;
    }
    @Override
    public User getTrueUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("Fetching all users");
        Iterable<User> usersList = userRepo.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : usersList) {
            UserDTO userDTO = userMapper.userToUserDTO(user);
            userDTO.setPassword("hidden");
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}

 */
