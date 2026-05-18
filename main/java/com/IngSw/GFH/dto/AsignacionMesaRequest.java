package com.IngSw.GFH.dto;

import jakarta.validation.constraints.NotNull;

public class AsignacionMesaRequest {

    @NotNull(message = "El ID del mesero es obligatorio")
    private Long meseroId;

    @NotNull(message = "El ID de la mesa es obligatorio")
    private Long mesaId;

    public AsignacionMesaRequest() {}

    public Long getMeseroId()           { return meseroId; }
    public void setMeseroId(Long id)    { this.meseroId = id; }

    public Long getMesaId()             { return mesaId; }
    public void setMesaId(Long id)      { this.mesaId = id; }
}
