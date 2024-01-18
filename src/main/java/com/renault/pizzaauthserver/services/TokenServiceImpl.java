package com.renault.pizzaauthserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.renault.pizzaauthserver.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private UserRepo userRepo;

    private final String secret = "secret";
    private long accessTimeout = 10*60*1000;
    private long refreshTimeout = 30*60*1000;

    public TokenServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Map<String, String> generateToken(User user, HttpServletRequest request) {
        String access_token = JWT.create()
                .withSubject(user.getUsername()) //Should choose something unique that identifies (in this app usernames are uniques)
                .withExpiresAt(new Date(System.currentTimeMillis() +accessTimeout)) // Access Token (Bearer) will expire in 10 minutes
                .withIssuer(request.getRequestURL().toString()) //Company name or author of this
                .withClaim("permissions", user.getAuthorities().stream() //Roles of this specific user
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(this.getAlgorithm()); //must sign the Token with the algorithm created
        String refresh_token = JWT.create()
                .withSubject(user.getUsername()) //Should choose something unique that identifies (in this app usernames are uniques)
                .withExpiresAt(new Date(System.currentTimeMillis() +refreshTimeout)) // Refresh Token (creates new AccessTokens) will expire in 30 minutes
                .withIssuer(request.getRequestURL().toString()) //Company name or author of this
                .sign(this.getAlgorithm()); //must sign the Token with the algorithm created
        // Response to the Front-end, giving them headers
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        return tokens;
    }

    @Override
    public UsernamePasswordAuthenticationToken validateAndGetAuthToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //Use the same secret as signing (Authenticating) user.
        //JWTVerifier assert Token and Signatures
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("permissions").asArray(String.class); //Variable name in User Class

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });

        return new UsernamePasswordAuthenticationToken(username,null,authorities);
    }



    private Algorithm getAlgorithm() {
        //'secret' would be encrypted somewhere and stored safely
        return Algorithm.HMAC256(this.secret.getBytes());
    }




}
