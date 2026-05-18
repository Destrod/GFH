package com.IngSw.GFH.dto;

import java.math.BigDecimal;

public class InformeFinancieroResponse {

    private BigDecimal totalIngresos;
    private BigDecimal totalEgresos;
    private BigDecimal ganancias;

    public InformeFinancieroResponse() {}

    public InformeFinancieroResponse(BigDecimal totalIngresos,
                                     BigDecimal totalEgresos,
                                     BigDecimal ganancias) {
        this.totalIngresos = totalIngresos;
        this.totalEgresos  = totalEgresos;
        this.ganancias     = ganancias;
    }

    public static InformeFinancieroResponse calcular(BigDecimal ingresos,
                                                     BigDecimal egresos) {
        return new InformeFinancieroResponse(ingresos, egresos,
                ingresos.subtract(egresos));
    }

    // ── Getters ──────────────────────────────────────────────

    public BigDecimal getTotalIngresos() { return totalIngresos; }
    public BigDecimal getTotalEgresos()  { return totalEgresos; }
    public BigDecimal getGanancias()     { return ganancias; }

    // ── Setters ──────────────────────────────────────────────

    public void setTotalIngresos(BigDecimal totalIngresos) { this.totalIngresos = totalIngresos; }
    public void setTotalEgresos(BigDecimal totalEgresos)   { this.totalEgresos = totalEgresos; }
    public void setGanancias(BigDecimal ganancias)         { this.ganancias = ganancias; }
}
