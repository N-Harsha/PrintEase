package com.example.printease.security.Jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${printEase.app.jwtSecret}")
    private String jwtSecret;

    @Value("${printEase.app.jwtExpirationSeconds}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetails userPrinciple = (UserDetails) authentication.getPrincipal();

        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        return Jwts.builder()
                .setSubject((userPrinciple.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(zonedIST.plusSeconds(jwtExpirationMs).toInstant()))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e){
            logger.error("Invalid JWT signature: {}",e.getMessage());
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT token: {}",e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("JWT Token id expired: {}",e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("JWT Token unsupported: {}",e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
