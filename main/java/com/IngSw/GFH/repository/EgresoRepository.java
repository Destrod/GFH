package com.IngSw.GFH.repository;

import com.IngSw.GFH.model.Egreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EgresoRepository extends JpaRepository<Egreso, Long> {

    @Query("SELECT COALESCE(SUM(e.valor), 0) FROM Egreso e")
    BigDecimal sumaTotalEgresos();
}
