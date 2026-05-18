package com.IngSw.GFH.config;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Mesa;
import com.IngSw.GFH.model.Rol;
import com.IngSw.GFH.repository.EmpleadoRepository;
import com.IngSw.GFH.repository.MesaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos: crea un administrador por defecto y mesas
 * si la base de datos está vacía al arrancar la aplicación.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    private final MesaRepository mesaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EmpleadoRepository empleadoRepository,
                           MesaRepository mesaRepository,
                           PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.mesaRepository = mesaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crearAdminPorDefecto();
        crearMesasPorDefecto();
    }

    private void crearAdminPorDefecto() {
        if (!empleadoRepository.existsByNombreUsuario("admin")) {
            Empleado admin = new Empleado(
                    "Administrador",
                    "Principal",
                    "admin",
                    Rol.ADMINISTRADOR,
                    passwordEncoder.encode("admin123")
            );
            empleadoRepository.save(admin);
            System.out.println(">>> Admin por defecto creado: usuario=admin | contraseña=admin123");
        }
    }

    private void crearMesasPorDefecto() {
        if (mesaRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                mesaRepository.save(new Mesa(i));
            }
            System.out.println(">>> 10 mesas creadas por defecto.");
        }
    }
}