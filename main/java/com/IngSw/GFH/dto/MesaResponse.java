package com.IngSw.GFH.dto;

import com.IngSw.GFH.model.Mesa;

public class MesaResponse {

    private Long id;
    private Integer numeroMesa;
    private boolean disponible;
    private Long meseroId;
    private String meseroNombre;

    public MesaResponse() {}

    public static MesaResponse desde(Mesa mesa) {
        MesaResponse dto = new MesaResponse();
        dto.id         = mesa.getId();
        dto.numeroMesa = mesa.getNumeroMesa();
        dto.disponible = mesa.isDisponible();
        if (mesa.getMesero() != null) {
            dto.meseroId     = mesa.getMesero().getId();
            dto.meseroNombre = mesa.getMesero().getNombreCompleto();
        }
        return dto;
    }

    // ── Getters ──────────────────────────────────────────────

    public Long getId()              { return id; }
    public Integer getNumeroMesa()   { return numeroMesa; }
    public boolean isDisponible()    { return disponible; }
    public Long getMeseroId()        { return meseroId; }
    public String getMeseroNombre()  { return meseroNombre; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(Long id)                      { this.id = id; }
    public void setNumeroMesa(Integer numeroMesa)   { this.numeroMesa = numeroMesa; }
    public void setDisponible(boolean disponible)   { this.disponible = disponible; }
    public void setMeseroId(Long meseroId)          { this.meseroId = meseroId; }
    public void setMeseroNombre(String meseroNombre){ this.meseroNombre = meseroNombre; }
}
