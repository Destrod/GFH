package com.IngSw.GFH.config;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Mesa;
import com.IngSw.GFH.model.Rol;
import com.IngSw.GFH.repository.EmpleadoRepository;
import com.IngSw.GFH.repository.MesaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    private final MesaRepository mesaRepository;

    public DataInitializer(EmpleadoRepository empleadoRepository,
                           MesaRepository mesaRepository) {
        this.empleadoRepository = empleadoRepository;
        this.mesaRepository = mesaRepository;
    }

    @Override
    public void run(String... args) {
        sincronizarAdmin();
        crearMesasPorDefecto();
    }

    private void sincronizarAdmin() {
        Optional<Empleado> existente = empleadoRepository.findByNombreUsuario("admin");

        if (!existente.isPresent()) {
            Empleado admin = new Empleado(
                    "Administrador",
                    "Principal",
                    "admin",
                    Rol.ADMINISTRADOR,
                    "admin123"   // texto plano — sin BCrypt
            );
            empleadoRepository.save(admin);
            System.out.println(">>> Admin creado: usuario=admin | contrasena=admin123");
        } else {
            Empleado admin = existente.get();
            admin.setContrasena("admin123");
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
            System.out.println(">>> 10 mesas creadas.");
        }
    }
}