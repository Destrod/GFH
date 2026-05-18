package com.IngSw.GFH.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    public LoginRequest() {}

    public String getNombreUsuario()         { return nombreUsuario; }
    public void setNombreUsuario(String u)   { this.nombreUsuario = u; }

    public String getContrasena()            { return contrasena; }
    public void setContrasena(String c)      { this.contrasena = c; }
}
