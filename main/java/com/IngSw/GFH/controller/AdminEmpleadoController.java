package com.IngSw.GFH.controller;

import com.IngSw.GFH.dto.ApiResponse;
import com.IngSw.GFH.dto.EmpleadoRequest;
import com.IngSw.GFH.dto.EmpleadoResponse;
import com.IngSw.GFH.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/empleados")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminEmpleadoController {

    private final EmpleadoService empleadoService;

    public AdminEmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpleadoResponse>> registrarEmpleado(
            @Valid @RequestBody EmpleadoRequest request) {

        EmpleadoResponse empleado = empleadoService.registrarEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.exitoso("Empleado registrado exitosamente", empleado));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpleadoResponse>>> consultarEmpleados() {
        List<EmpleadoResponse> empleados = empleadoService.consultarEmpleados();
        return ResponseEntity.ok(
                ApiResponse.exitoso("Lista de empleados obtenida", empleados));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoResponse>> consultarEmpleadoPorId(
            @PathVariable Long id) {

        EmpleadoResponse empleado = empleadoService.consultarEmpleadoPorId(id);
        return ResponseEntity.ok(
                ApiResponse.exitoso("Empleado encontrado", empleado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.ok(
                ApiResponse.exitoso("Empleado eliminado exitosamente"));
    }
}