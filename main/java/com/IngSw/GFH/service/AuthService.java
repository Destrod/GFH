package com.IngSw.GFH.service;

import com.IngSw.GFH.config.JwtUtil;
import com.IngSw.GFH.dto.LoginRequest;
import com.IngSw.GFH.dto.LoginResponse;
import com.IngSw.GFH.exception.AutenticacionException;
import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.repository.EmpleadoRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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

    public LoginResponse login(LoginRequest request) {

        // Validación de campos vacíos
        if (request.getNombreUsuario() == null || request.getNombreUsuario().isBlank()) {
            throw new AutenticacionException("El nombre de usuario es obligatorio");
        }
        if (request.getContrasena() == null || request.getContrasena().isBlank()) {
            throw new AutenticacionException("La contraseña es obligatoria");
        }

        System.out.println("[AUTH] Intentando login para: " + request.getNombreUsuario());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNombreUsuario(),
                            request.getContrasena()
                    )
            );
        } catch (DisabledException e) {
            System.out.println("[AUTH] Usuario desactivado: " + request.getNombreUsuario());
            throw new AutenticacionException("El usuario está desactivado");
        } catch (BadCredentialsException e) {
            System.out.println("[AUTH] Credenciales incorrectas para: " + request.getNombreUsuario());
            throw new AutenticacionException("Usuario o contraseña incorrectos");
        } catch (AuthenticationException e) {
            System.out.println("[AUTH] Error de autenticación: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
            throw new AutenticacionException("Error de autenticación: " + e.getMessage());
        }

        System.out.println("[AUTH] Login exitoso para: " + request.getNombreUsuario());

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