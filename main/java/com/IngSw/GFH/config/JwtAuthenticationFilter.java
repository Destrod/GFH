package com.IngSw.GFH.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT — intercepta cada petición y valida el token Bearer.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[JWT] Header Authorization ausente o sin prefijo Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token quitando "Bearer " (7 caracteres)
        final String jwt = authHeader.substring(7).trim();

        if (jwt.isEmpty()) {
            System.out.println("[JWT] Token vacío después de Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String nombreUsuario = jwtUtil.extraerNombreUsuario(jwt);
            System.out.println("[JWT] Usuario extraído del token: " + nombreUsuario);

            if (nombreUsuario != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                if (jwtUtil.esTokenValido(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("[JWT] Autenticación establecida para: " + nombreUsuario
                            + " | Roles: " + userDetails.getAuthorities());
                } else {
                    System.out.println("[JWT] Token inválido o expirado para: " + nombreUsuario);
                }
            }

        } catch (Exception e) {
            // Imprime el error real en consola para diagnóstico
            System.out.println("[JWT] Error al procesar el token: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}