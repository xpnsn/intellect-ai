package com.bohemian.intellect.service;

import com.bohemian.intellect.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails)userDetails;
        return (((Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .header().empty())
                .add("typ", "JWT")).and())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(this.getSignKey())
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return this.extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return this.extractClaims(token).getExpiration();
    }

    public SecretKey getSignKey() {
        byte[] decode = Decoders.BASE64.decode(this.JWT_SECRET);
        return Keys.hmacShaKeyFor(decode);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(this.getSignKey()).build().parseSignedClaims(token).getPayload();
    }
}
