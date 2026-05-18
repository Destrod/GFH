package com.IngSw.GFH.controller;

import com.IngSw.GFH.dto.ApiResponse;
import com.IngSw.GFH.dto.AsignacionMesaRequest;
import com.IngSw.GFH.dto.MesaResponse;
import com.IngSw.GFH.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/mesas")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminMesaController {

    private final MesaService mesaService;

    public AdminMesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MesaResponse>>> consultarTodasLasMesas() {
        List<MesaResponse> mesas = mesaService.consultarTodasLasMesas();
        return ResponseEntity.ok(
                ApiResponse.exitoso("Mesas obtenidas", mesas));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<MesaResponse>>> consultarMesasDisponibles() {
        List<MesaResponse> mesas = mesaService.consultarMesasDisponibles();
        return ResponseEntity.ok(
                ApiResponse.exitoso("Mesas disponibles obtenidas", mesas));
    }

    @GetMapping("/asignadas")
    public ResponseEntity<ApiResponse<List<MesaResponse>>> consultarMesasAsignadas() {
        List<MesaResponse> mesas = mesaService.consultarMesasAsignadas();
        return ResponseEntity.ok(
                ApiResponse.exitoso("Mesas asignadas obtenidas", mesas));
    }

    @PostMapping("/asignar")
    public ResponseEntity<ApiResponse<MesaResponse>> asignarMesa(
            @Valid @RequestBody AsignacionMesaRequest request) {

        MesaResponse mesa = mesaService.asignarMesa(request);
        return ResponseEntity.ok(
                ApiResponse.exitoso("Mesa asignada exitosamente", mesa));
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<ApiResponse<MesaResponse>> liberarMesa(@PathVariable Long id) {
        MesaResponse mesa = mesaService.liberarMesa(id);
        return ResponseEntity.ok(
                ApiResponse.exitoso("Mesa liberada exitosamente", mesa));
    }
}