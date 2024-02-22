package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.*;
import com.renault.pizzaauthserver.domain.*;
import com.renault.pizzaauthserver.repositories.RoleRepo;
import com.renault.pizzaauthserver.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponseDTO register(AuthRegisterDTO register) {
        //check if username already exists
        boolean isUsernamePresent = userRepository.findByUsername(register.getUsername()).isPresent();
        if (isUsernamePresent) {
            log.info("INFO<AuthService>: username {"+register.getUsername()+"} already in DB, refusing registration.");
            throw new UsernameTakenException();
        }
        //In case username not found, do registry
        var user = User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(this.createAndSaveUserRole())
                .build();
        userRepository.save(user);
        log.info("INFO<AuthService>: new User with username: {"+register.getUsername()+"} created in DB");
        return this.generateTokenAndCreateResponse(user);
    }
    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authentication) {
        //AuthManager will throw AuthenticationException in case is not passed
        log.info("INFO<AuthService>: authenticate with: "+authentication.getUsername()+" and "+authentication.getPassword());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authentication.getUsername(),
                        authentication.getPassword())
        );
        log.info("INFO<AuthService>: authentication passed for user: {"+authentication.getUsername()+"}");
        var user = userRepository.findByUsername(authentication.getUsername())
                .orElseThrow();
        return this.generateTokenAndCreateResponse(user);
    }

    @Override
    public AuthIntrospectResponseDTO introspect(AuthIntrospectRequestDTO introspectRequest) {
        log.info("INFO<AuthService>: introspect method accessed");
        if (isUserLoadedIntoContext()) {
            evaluatePermissions(introspectRequest);
            return AuthIntrospectResponseDTO.builder()
                    .status("session valid")
                    .build();
        }
        log.info("INFO<AuthService>: user is not authenticated");
        throw new SessionAuthenticationException("session for this user is not initiated");
    }

    private void evaluatePermissions(AuthIntrospectRequestDTO introspectRequest) {
        boolean hasPermission = false;
        String requestedPermission = introspectRequest.getRequestedPermission();
        for (String permission: getUserLoadedIntoContext().getRole().getPermissions()) {
            //format in DB: "condition:permission"
            String[] userConditionPermission = permission.split(":",2);
            String userPermission = userConditionPermission[2];
            //Check if permission is ok
            if (requestedPermission.equals(userPermission)) {
                String userCondition = userConditionPermission[1];
                if (Objects.equals(userCondition, "any")) {
                    hasPermission = true;
                    break;
                } else if (Objects.equals(userCondition, "owner")) {
                    if (Objects.equals(introspectRequest.getUsernameImpacted(), getUserLoadedIntoContext().getUsername())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            //

        }
        if (!hasPermission) {
            throw new NoPermissionException();
        }
    }


    @Override
    public AuthResponseDTO refresh() {
        log.info("INFO<AuthService>: refresh method accessed");
        if (isUserLoadedIntoContext()) {
            return this.generateTokenAndCreateResponse(getUserLoadedIntoContext());
        }
        log.info("INFO<AuthService>: user is not authenticated");
        throw new SessionAuthenticationException("session for this user is not initiated");
    }

    @Override
    public void logout(String token) {
        log.info("INFO<AuthService>: refresh method accessed");
        if (isUserLoadedIntoContext()) {
            this.jwtService.addTokenToBlackList(token);
        }
        log.info("INFO<AuthService>: user is not authenticated");
        throw new SessionAuthenticationException("session for this user is not initiated");
    }

    private Role createAndSaveUserRole() {
        Role newUserRole = Role.builder()
                .name("user")
                .permissions(RoleList.USER.getPermissions())
                .build();
        return roleRepository.save(newUserRole);
    }
    private AuthResponseDTO generateTokenAndCreateResponse(User user) {
        return AuthResponseDTO.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
    private boolean isUserLoadedIntoContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return principal instanceof User;
        }
        throw new UsernameNotFoundException("Username not found in context");
    }
    private User getUserLoadedIntoContext() {
        if (isUserLoadedIntoContext()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }
}
