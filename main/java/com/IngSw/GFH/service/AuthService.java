package com.IngSw.GFH.service;

import com.IngSw.GFH.config.JwtUtil;
import com.IngSw.GFH.dto.LoginRequest;
import com.IngSw.GFH.dto.LoginResponse;
import com.IngSw.GFH.exception.AutenticacionException;
import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.repository.EmpleadoRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación.
 * CU-01 / CU-02: Valida credenciales, identifica rol y genera token JWT.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final EmpleadoRepository empleadoRepository;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       EmpleadoRepository empleadoRepository,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.empleadoRepository = empleadoRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica al usuario y retorna el token JWT con el rol.
     * CU-02: Flujo principal.
     */
    public LoginResponse login(LoginRequest request) {

        // CU-02: Flujo alternativo — campos vacíos
        // Validación manual para no depender de jakarta.validation en el DTO
        if (request.getNombreUsuario() == null || request.getNombreUsuario().isBlank()) {
            throw new AutenticacionException("El nombre de usuario es obligatorio");
        }
        if (request.getContrasena() == null || request.getContrasena().isBlank()) {
            throw new AutenticacionException("La contraseña es obligatoria");
        }

        // CU-02: Flujo alternativo — credenciales incorrectas
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNombreUsuario(),
                            request.getContrasena()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new AutenticacionException("Usuario o contraseña incorrectos");
        }

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getNombreUsuario());
        String token = jwtUtil.generarToken(userDetails);

        Empleado empleado = empleadoRepository
                .findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow(() -> new AutenticacionException("Usuario no encontrado"));

        return new LoginResponse(
                token,
                empleado.getNombreUsuario(),
                empleado.getNombreCompleto(),
                empleado.getRol(),
                "Inicio de sesión exitoso"
        );
    }
}
