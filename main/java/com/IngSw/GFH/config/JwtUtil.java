package com.IngSw.GFH.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        // HMAC-SHA256 requiere mínimo 32 bytes — rellenar si es más corta
        if (keyBytes.length < 32) {
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, keyBytes.length);
            keyBytes = padded;
        }
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generarToken(UserDetails userDetails) {
        Date ahora      = new Date();
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
        System.out.println("[JWT] Validando — Usuario: " + nombreUsuario
                + " | Válido: " + valido);
        return valido;
    }

    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}