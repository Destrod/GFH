package com.IngSw.GFH.dto;

import com.IngSw.GFH.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para registrar un empleado.
 * CU-09: Campos requeridos nombre, apellido, nombreUsuario, rol, contraseña.
 */
public class EmpleadoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    public EmpleadoRequest() {}

    public String getNombre()               { return nombre; }
    public void setNombre(String n)         { this.nombre = n; }

    public String getApellido()             { return apellido; }
    public void setApellido(String a)       { this.apellido = a; }

    public String getNombreUsuario()        { return nombreUsuario; }
    public void setNombreUsuario(String u)  { this.nombreUsuario = u; }

    public Rol getRol()                     { return rol; }
    public void setRol(Rol r)               { this.rol = r; }

    public String getContrasena()           { return contrasena; }
    public void setContrasena(String c)     { this.contrasena = c; }
}
