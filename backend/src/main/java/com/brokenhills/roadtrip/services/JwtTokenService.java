package com.brokenhills.roadtrip.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenService {

    private static final long JWT_TOKEN_VALIDITY = 5*60*60;

    @Value("${key-store.path}")
    private String keystorePath;
    @Value("${key-store.password}")
    private String keystorePassword;
    @Value("${key-store.alias}")
    private String keystoreAlias;

    @Bean
    public Key getJwtKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(Files.newInputStream(Paths.get(keystorePath)), keystorePassword.toCharArray());
            byte[] keyBytes = keyStore.getKey(keystoreAlias, keystorePassword.toCharArray()).getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | KeyStoreException
                | CertificateException | UnrecoverableKeyException e) {
            log.error("Error creating jwt private key: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isValidFormat(String authHeaderValue) {
        return authHeaderValue != null && authHeaderValue.startsWith("Bearer ");
    }

    /**
     * JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
     * @param authHeaderValue value of the Authorization request header
     * @return token value without "Bearer "
     */
    public String extractToken(String authHeaderValue) {
        return authHeaderValue.substring(7);
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getJwtKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.RS512, getJwtKey()).compact();
    }
}
