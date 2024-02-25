package com.renault.pizzaauthserver.services;

import com.renault.pizzaauthserver.config.ApplicationConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
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
//    private static String SECRET_KEY;
    public static Set<String> blackListTokens;
    
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
                .issuer("pizza-auth-token")
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ApplicationConfig.tokenExpirationTime))
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
        //Checks if the token belongs to this User, is not expired and is not part of the BlackList
        if (extractUsername(token).equals(userDetails.getUsername())) {
            if (!isTokenExpired(token)) {
                return !this.isTokenInBlackList(token);
            }
        }
        return false;
    }

    @Override
    public void addTokenToBlackList(String token) {
        getBlackListTokens().add(token);
        log.info("INFO <JwtServiceImpl>: new token added to blacklist, with now size of "+getBlackListTokens().size());
    }

    private boolean isTokenInBlackList(String token) {
        return getBlackListTokens().contains(token);
    }

    private Set<String> getBlackListTokens() {
        if (blackListTokens == null) {
            blackListTokens = new HashSet<>();
        }
        return blackListTokens;
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
        byte[] keyBytes= Decoders.BASE64.decode(ApplicationConfig.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
