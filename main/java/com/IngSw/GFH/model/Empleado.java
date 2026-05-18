package com.IngSw.GFH.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un empleado del restaurante.
 * CU-03 / CU-09: Campos requeridos para el registro de un empleado.
 *
 * NOTA: Las validaciones (@NotBlank, @NotNull) se aplican en los DTOs
 * (EmpleadoRequest, LoginRequest) y no en la entidad JPA directamente.
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private boolean activo = true;

    // Constructor vacío requerido por JPA
    public Empleado() {}

    public Empleado(String nombre, String apellido, String nombreUsuario,
                    Rol rol, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.contrasena = contrasena;
        this.activo = true;
    }

    // ── Getters ──────────────────────────────────────────────

    public Long getId()                { return id; }
    public String getNombre()          { return nombre; }
    public String getApellido()        { return apellido; }
    public String getNombreUsuario()   { return nombreUsuario; }
    public Rol getRol()                { return rol; }
    public String getContrasena()      { return contrasena; }
    public boolean isActivo()          { return activo; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(Long id)                       { this.id = id; }
    public void setNombre(String nombre)             { this.nombre = nombre; }
    public void setApellido(String apellido)         { this.apellido = apellido; }
    public void setNombreUsuario(String u)           { this.nombreUsuario = u; }
    public void setRol(Rol rol)                      { this.rol = rol; }
    public void setContrasena(String contrasena)     { this.contrasena = contrasena; }
    public void setActivo(boolean activo)            { this.activo = activo; }

    // ── Métodos de negocio ───────────────────────────────────

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}