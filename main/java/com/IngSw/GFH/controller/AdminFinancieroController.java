package com.IngSw.GFH.controller;

import com.IngSw.GFH.dto.ApiResponse;
import com.IngSw.GFH.dto.EgresoRequest;
import com.IngSw.GFH.dto.InformeFinancieroResponse;
import com.IngSw.GFH.service.FinancieroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/financiero")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminFinancieroController {

    private final FinancieroService financieroService;

    public AdminFinancieroController(FinancieroService financieroService) {
        this.financieroService = financieroService;
    }

    @PostMapping("/egresos")
    public ResponseEntity<ApiResponse<Void>> registrarEgreso(
            @Valid @RequestBody EgresoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        financieroService.registrarEgreso(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.exitoso("Egreso registrado exitosamente"));
    }

    @GetMapping("/informe")
    public ResponseEntity<ApiResponse<InformeFinancieroResponse>> consultarInforme() {
        InformeFinancieroResponse informe = financieroService.consultarInformeFinanciero();
        return ResponseEntity.ok(
                ApiResponse.exitoso("Informe financiero generado", informe));
    }
}
