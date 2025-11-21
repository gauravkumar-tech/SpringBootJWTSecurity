package com.codewithgaurav.jwtsecurity.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtil {

    final static String SECRET = "A3E78575B2911E838167362728193F215885F63";
    final static SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    //generate
    public static String generateToken(String username) {
       return Jwts.builder()
               .subject(username)
               .issuer("CodeWithGaurav97")
               .expiration(Date.from(Instant.now().plusSeconds(3600)))
               .signWith(key)
               .compact();
    }

    //getUsername
    public static String getUsername(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public static boolean validateToken(String token) {
        try{
            getUsername(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
