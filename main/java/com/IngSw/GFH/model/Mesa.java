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

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesero_id")
    private Empleado mesero;

    @Column(nullable = false)
    private boolean disponible = true;

    // Constructor vacío requerido por JPA
    public Mesa() {}

    public Mesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.disponible = true;
    }

    // ── Getters ──────────────────────────────────────────────

    public Long getId()            { return id; }
    public Integer getNumeroMesa() { return numeroMesa; }
    public Empleado getMesero()    { return mesero; }
    public boolean isDisponible()  { return disponible; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(Long id)                 { this.id = id; }
    public void setNumeroMesa(Integer n)       { this.numeroMesa = n; }
    public void setMesero(Empleado mesero)     { this.mesero = mesero; }
    public void setDisponible(boolean d)       { this.disponible = d; }

    // ── Métodos de negocio ───────────────────────────────────

    public void asignarMesero(Empleado mesero) {
        this.mesero = mesero;
        this.disponible = false;
    }

    public void liberarMesa() {
        this.mesero = null;
        this.disponible = true;
    }
}
