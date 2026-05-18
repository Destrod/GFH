package com.IngSw.GFH.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "egresos")
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String concepto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por_id")
    private Empleado registradoPor;

    // Constructor vacío requerido por JPA
    public Egreso() {}

    public Egreso(String concepto, BigDecimal valor, LocalDate fecha,
                  Empleado registradoPor) {
        this.concepto = concepto;
        this.valor = valor;
        this.fecha = fecha;
        this.registradoPor = registradoPor;
    }

    // ── Getters ──────────────────────────────────────────────

    public Long getId()                    { return id; }
    public String getConcepto()            { return concepto; }
    public BigDecimal getValor()           { return valor; }
    public LocalDate getFecha()            { return fecha; }
    public Empleado getRegistradoPor()     { return registradoPor; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(Long id)                         { this.id = id; }
    public void setConcepto(String concepto)           { this.concepto = concepto; }
    public void setValor(BigDecimal valor)             { this.valor = valor; }
    public void setFecha(LocalDate fecha)              { this.fecha = fecha; }
    public void setRegistradoPor(Empleado e)           { this.registradoPor = e; }
}
