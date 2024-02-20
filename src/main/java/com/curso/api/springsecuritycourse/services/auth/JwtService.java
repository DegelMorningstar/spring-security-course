package com.curso.api.springsecuritycourse.services.auth;

import com.curso.api.springsecuritycourse.persistence.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_TIME;
    @Value("${security.jwt.secret_key}")
    private String SECRET_KEY;


    public String generateToken(UserDetails user,Map<String,Object> extraClaims) {

        Date issueAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_TIME * 60 * 1000) + issueAt.getTime());

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issueAt)
                .expiration(expiration)
                .signWith(generateKey(),Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey generateKey() {

        byte[] passwordDecode = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(passwordDecode);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }
}
