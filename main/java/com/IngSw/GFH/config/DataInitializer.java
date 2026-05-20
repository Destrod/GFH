package com.IngSw.GFH.config;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Mesa;
import com.IngSw.GFH.model.Rol;
import com.IngSw.GFH.repository.EmpleadoRepository;
import com.IngSw.GFH.repository.MesaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Inicializador de datos: crea o reinicia el administrador por defecto
 * y crea las mesas si no existen.
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
        sincronizarAdmin();
        crearMesasPorDefecto();
    }

    /**
     * Crea el admin si no existe, o resetea su contraseña si ya existe.
     * Garantiza que siempre se pueda hacer login con admin / admin123.
     */
    private void sincronizarAdmin() {
        Optional<Empleado> existente = empleadoRepository.findByNombreUsuario("admin");

        if (!existente.isPresent()) {
            // Crear admin por primera vez
            Empleado admin = new Empleado(
                    "Administrador",
                    "Principal",
                    "admin",
                    Rol.ADMINISTRADOR,
                    passwordEncoder.encode("admin123")
            );
            empleadoRepository.save(admin);
            System.out.println(">>> Admin creado: usuario=admin | contrasena=admin123");

        } else {
            // Admin ya existe — resetear contraseña para garantizar acceso
            Empleado admin = existente.get();
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setActivo(true);
            admin.setRol(Rol.ADMINISTRADOR);
            empleadoRepository.save(admin);
            System.out.println(">>> Admin sincronizado: usuario=admin | contrasena=admin123");
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