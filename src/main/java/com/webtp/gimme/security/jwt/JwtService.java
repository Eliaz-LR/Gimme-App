package com.webtp.gimme.security.jwt;

import com.webtp.gimme.security.CustomerDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("my-secure-key-with-at-least-32-characters".getBytes(StandardCharsets.UTF_8));

    private final long EXPIRATION_TIME = 86400000L;

    private static final Instant serverStartTime = Instant.now();

    public String generateToken(CustomerDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("serverStartTime", serverStartTime.toString()) // Ajout du timestamp
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }

            Instant tokenStartTime = Instant.parse(claims.get("serverStartTime", String.class));
            return !tokenStartTime.isBefore(serverStartTime);

        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token, CustomerDetails customerDetails) {
        return new UsernamePasswordAuthenticationToken(customerDetails, "", customerDetails.getAuthorities());
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
