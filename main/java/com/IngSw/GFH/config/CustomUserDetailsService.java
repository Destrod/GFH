package com.IngSw.GFH.config;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.repository.EmpleadoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    public CustomUserDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository
                .findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + nombreUsuario));

        // Prefijo ROLE_ requerido por Spring Security para hasRole()
        String rol = "ROLE_" + empleado.getRol().name();

        return new User(
                empleado.getNombreUsuario(),
                empleado.getContrasena(),
                empleado.isActivo(),
                true, true, true,
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}
