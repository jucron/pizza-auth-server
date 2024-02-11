package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.api.v1.model.AuthRegisterDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthRequestDTO;
import com.renault.pizzaauthserver.api.v1.model.AuthResponseDTO;
import com.renault.pizzaauthserver.domain.Role;
import com.renault.pizzaauthserver.domain.RoleList;
import com.renault.pizzaauthserver.domain.User;
import com.renault.pizzaauthserver.repositories.RoleRepo;
import com.renault.pizzaauthserver.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponseDTO register(AuthRegisterDTO register) {
        var user = User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(this.createAndSaveUserRole())
                .build();
        userRepository.save(user);
        return this.generateTokenAndCreateResponse(user);
    }
    public AuthResponseDTO authenticate(AuthRequestDTO authentication) {
        //AuthManager will throw AuthenticationException in case is not passed
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authentication.getUsername(),
                        authentication.getPassword())
        );
        var user = userRepository.findByUsername(authentication.getUsername())
                .orElseThrow();
        return this.generateTokenAndCreateResponse(user);
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
}
