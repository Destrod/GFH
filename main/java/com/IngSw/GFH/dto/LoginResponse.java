package com.IngSw.GFH.dto;

import com.IngSw.GFH.model.Rol;

public class LoginResponse {

    private String token;
    private String nombreUsuario;
    private String nombreCompleto;
    private Rol rol;
    private String mensaje;

    public LoginResponse() {}

    public LoginResponse(String token, String nombreUsuario, String nombreCompleto,
                         Rol rol, String mensaje) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.mensaje = mensaje;
    }

    // ── Getters ──────────────────────────────────────────────

    public String getToken()           { return token; }
    public String getNombreUsuario()   { return nombreUsuario; }
    public String getNombreCompleto()  { return nombreCompleto; }
    public Rol getRol()                { return rol; }
    public String getMensaje()         { return mensaje; }

    // ── Setters ──────────────────────────────────────────────

    public void setToken(String token)                   { this.token = token; }
    public void setNombreUsuario(String nombreUsuario)   { this.nombreUsuario = nombreUsuario; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setRol(Rol rol)                          { this.rol = rol; }
    public void setMensaje(String mensaje)               { this.mensaje = mensaje; }
}