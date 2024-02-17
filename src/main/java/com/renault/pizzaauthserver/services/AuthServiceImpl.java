package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.AuthIntrospectDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRegisterDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRequestDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthResponseDTO;
import com.renault.pizzaauthserver.domain.Role;
import com.renault.pizzaauthserver.domain.RoleList;
import com.renault.pizzaauthserver.domain.User;
import com.renault.pizzaauthserver.domain.UsernameTakenException;
import com.renault.pizzaauthserver.repositories.RoleRepo;
import com.renault.pizzaauthserver.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

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
    public AuthIntrospectDTO introspect() {
        log.info("INFO<AuthService>: introspect method accessed");
        if (isUserLoadedIntoContext()) {
            return AuthIntrospectDTO.builder()
                    .status("session valid")
                    .build();
        }
        log.info("INFO<AuthService>: user is not authenticated");
        throw new SessionAuthenticationException("session for this user is not initiated");
    }



    @Override
    public AuthResponseDTO refresh() {
        log.info("INFO<AuthService>: refresh method accessed");
        if (isUserLoadedIntoContext()) {
            return this.generateTokenAndCreateResponse(getUserLoadedIntoContext());
        }
        log.info("INFO<AuthService>: user is not authenticated");
        //This will never be reached since the ValidateToken will throw Exception
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
        return principal instanceof User;
    }
    private User getUserLoadedIntoContext() {
        if (isUserLoadedIntoContext()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }
}
