package com.renault.pizzaauthserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

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

    /** Secret-Key:
        Encrypt data from: https://generate-random.org/encryption-key-generator
        */
//    @Value("${spring.custom.secret-key}")
    private static final String SECRET_KEY = "d56888ccb91bcb8dbb3109bd3ace21c9c86c1e1df029676d0ac1e73a1b5ef47645b133bd015782b8baaac9780b982a07050412bf9acfc1fbd42f9a7e7d430df9";

    @Override
    public String extractUsername(String jwtToken) {
        //Subject of this Claim should be the username
        return extractClaim(jwtToken, Claims::getSubject);
    }
    @Override
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        //Generic method in order to be easy to extract any type of Claim
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //2 Token builders with 24hours of lifeCycle, one of them just have a subject
    @Override
    public String generateToken (Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
//                .signWith(getSignInKey(),SignatureAlgorithm.ES256)
                .signWith(getSignInKey())
                .compact();

    }
    @Override
    public String generateToken (UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        //Checks if the token belongs to this User and is not expired
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        //Checks if Token issue date is before today
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        //Will interpret jwtToken and extract payload containing all Claims
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