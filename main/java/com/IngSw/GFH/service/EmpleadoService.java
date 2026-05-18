package com.IngSw.GFH.service;


import com.IngSw.GFH.dto.EmpleadoRequest;
import com.IngSw.GFH.dto.EmpleadoResponse;
import com.IngSw.GFH.exception.RecursoNoEncontradoException;
import com.IngSw.GFH.exception.UsuarioDuplicadoException;
import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.repository.EmpleadoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public EmpleadoResponse registrarEmpleado(EmpleadoRequest request) {
        // CU-09: Flujo alternativo — nombre de usuario ya existe
        if (empleadoRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new UsuarioDuplicadoException(
                    "El nombre de usuario '" + request.getNombreUsuario() + "' ya se encuentra registrado");
        }

        Empleado empleado = new Empleado(
                request.getNombre(),
                request.getApellido(),
                request.getNombreUsuario(),
                request.getRol(),
                passwordEncoder.encode(request.getContrasena())
        );

        Empleado guardado = empleadoRepository.save(empleado);
        return EmpleadoResponse.desde(guardado);
    }

    public List<EmpleadoResponse> consultarEmpleados() {
        List<Empleado> empleados = empleadoRepository.findByActivoTrue();

        // CU-11: Flujo alternativo — no existen empleados registrados
        if (empleados.size() == 0) {
            throw new RecursoNoEncontradoException("No hay empleados disponibles para consultar");
        }

        List<EmpleadoResponse> resultado = new ArrayList<EmpleadoResponse>();
        for (Empleado empleado : empleados) {
            resultado.add(EmpleadoResponse.desde(empleado));
        }
        return resultado;
    }

    public EmpleadoResponse consultarEmpleadoPorId(Long id) {
        Optional<Empleado> opcional = empleadoRepository.findById(id);
        if (!opcional.isPresent()) {
            throw new RecursoNoEncontradoException("Empleado con ID " + id + " no encontrado");
        }
        return EmpleadoResponse.desde(opcional.get());
    }

    @Transactional
    public void eliminarEmpleado(Long id) {
        Optional<Empleado> opcional = empleadoRepository.findById(id);
        if (!opcional.isPresent()) {
            throw new RecursoNoEncontradoException("Empleado con ID " + id + " no encontrado");
        }

        Empleado empleado = opcional.get();
        // Baja lógica para preservar integridad referencial
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
}
