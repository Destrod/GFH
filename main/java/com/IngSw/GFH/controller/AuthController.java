package com.IngSw.GFH.controller;

import com.IngSw.GFH.dto.ApiResponse;
import com.IngSw.GFH.dto.LoginRequest;
import com.IngSw.GFH.dto.LoginResponse;
import com.IngSw.GFH.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.exitoso("Inicio de sesión exitoso", response));
    }
}
