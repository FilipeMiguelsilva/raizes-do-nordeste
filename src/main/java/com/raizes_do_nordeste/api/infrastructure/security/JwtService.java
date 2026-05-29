package com.raizes_do_nordeste.api.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

//classe que cria e le o token
@Service
public class JwtService {

    private static final String SECRET = "raizes-do-nordeste-chave-secreta-2026";
    private static final long EXPIRACAO = 86400000;

    private Key getChave() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String gerarToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(getChave(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extrairRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChave())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}