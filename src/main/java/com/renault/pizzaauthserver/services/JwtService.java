package com.renault.pizzaauthserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Service
public class JwtService {

    /** JSON Web Token:
    JWT contains Claims and are transferred between two parties
    Claims in JWT are encoded as JSON object that is digitally signed using a Json Web Signature
    JWT consists in 3 parts: Header, Payload and Signature
        > Header: Type of token and algorithm (ex sh-256 etc.)
        > Payload: Contain the Claims - statements about an Entity (typically the User and other Data)
            > types of Claims:
                    +registered - predefined, recommended but not mandatory
                        = provide a set of useful, repeatable claims
                        = ex: ISS (issuer), subject, expiration time, etc.
                    +public - defined within the IA and JWT registry or Public by nature
                    +private - custom Claims created to share private information between parties
        > Signature - Two goals:
                        = verify the Sender of the JWToken
                        = ensure that the message wasn't changed along the way
     */

    /*Secret-Key with Hex:
        Key: https://generate-random.org/encryption-key-generator
        Hex: https://crypt-online.ru/en/crypts/text2hex/
        */
    @Value("${spring.custom.secret-key}")
    private static String SECRET_KEY;
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
