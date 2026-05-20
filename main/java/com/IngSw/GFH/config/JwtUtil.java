package com.IngSw.GFH.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utilidad para generar y validar tokens JWT.
 * Usa jjwt 0.12.x.
 */
@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        // La clave DEBE estar en Base64 válido en application.properties
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(UserDetails userDetails) {
        Date ahora     = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationMs);

        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(getSigningKey())
                .compact();

        System.out.println("[JWT] Token generado para: " + userDetails.getUsername()
                + " | Expira: " + expiracion);
        return token;
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extraerNombreUsuario(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        final String nombreUsuario = extraerNombreUsuario(token);
        boolean valido = nombreUsuario.equals(userDetails.getUsername())
                && !estaExpirado(token);
        System.out.println("[JWT] Validando token — Usuario: " + nombreUsuario
                + " | Válido: " + valido);
        return valido;
    }

    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}