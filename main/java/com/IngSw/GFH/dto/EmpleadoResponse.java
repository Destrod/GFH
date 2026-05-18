package com.IngSw.GFH.dto;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Rol;

/**
 * DTO de respuesta con datos del empleado (sin contraseña).
 * CU-11: El sistema muestra la información correspondiente del empleado.
 */
public class EmpleadoResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private Rol rol;
    private boolean activo;

    public EmpleadoResponse() {}

    public static EmpleadoResponse desde(Empleado empleado) {
        EmpleadoResponse dto = new EmpleadoResponse();
        dto.id           = empleado.getId();
        dto.nombre       = empleado.getNombre();
        dto.apellido     = empleado.getApellido();
        dto.nombreUsuario = empleado.getNombreUsuario();
        dto.rol          = empleado.getRol();
        dto.activo       = empleado.isActivo();
        return dto;
    }

    // ── Getters ──────────────────────────────────────────────

    public Long getId()              { return id; }
    public String getNombre()        { return nombre; }
    public String getApellido()      { return apellido; }
    public String getNombreUsuario() { return nombreUsuario; }
    public Rol getRol()              { return rol; }
    public boolean isActivo()        { return activo; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(Long id)                        { this.id = id; }
    public void setNombre(String nombre)              { this.nombre = nombre; }
    public void setApellido(String apellido)          { this.apellido = apellido; }
    public void setNombreUsuario(String nombreUsuario){ this.nombreUsuario = nombreUsuario; }
    public void setRol(Rol rol)                       { this.rol = rol; }
    public void setActivo(boolean activo)             { this.activo = activo; }
}
