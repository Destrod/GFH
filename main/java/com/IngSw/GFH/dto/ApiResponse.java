package com.IngSw.GFH.dto;

public class ApiResponse<T> {

    private boolean exito;
    private String mensaje;
    private T datos;

    public ApiResponse() {}

    public static <T> ApiResponse<T> exitoso(String mensaje, T datos) {
        ApiResponse<T> response = new ApiResponse<>();
        response.exito  = true;
        response.mensaje = mensaje;
        response.datos   = datos;
        return response;
    }

    public static <T> ApiResponse<T> exitoso(String mensaje) {
        return exitoso(mensaje, null);
    }

    public static <T> ApiResponse<T> error(String mensaje) {
        ApiResponse<T> response = new ApiResponse<>();
        response.exito   = false;
        response.mensaje = mensaje;
        return response;
    }

    // ── Getters ──────────────────────────────────────────────

    public boolean isExito()   { return exito; }
    public String getMensaje() { return mensaje; }
    public T getDatos()        { return datos; }

    // ── Setters ──────────────────────────────────────────────

    public void setExito(boolean exito)    { this.exito = exito; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setDatos(T datos)          { this.datos = datos; }
}
