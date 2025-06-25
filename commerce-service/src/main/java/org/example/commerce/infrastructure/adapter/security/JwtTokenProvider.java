package org.example.commerce.infrastructure.adapter.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration-minutes}") private Long expMin;

    public String generarToken(String user,String rol){
        var now=new Date();
        var exp=new Date(now.getTime()+expMin*60_000);
        return Jwts.builder()
                .setSubject(user)
                .claim("rol",rol)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    public String obtenerUsuario(String token){
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }
}
