package com.IngSw.GFH.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EgresoRequest {

    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a cero")
    private BigDecimal valor;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    public EgresoRequest() {}

    public String getConcepto()             { return concepto; }
    public void setConcepto(String c)       { this.concepto = c; }

    public BigDecimal getValor()            { return valor; }
    public void setValor(BigDecimal v)      { this.valor = v; }

    public LocalDate getFecha()             { return fecha; }
    public void setFecha(LocalDate f)       { this.fecha = f; }
}
