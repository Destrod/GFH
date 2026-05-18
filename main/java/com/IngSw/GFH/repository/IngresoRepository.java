package com.IngSw.GFH.repository;

import com.IngSw.GFH.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    @Query("SELECT COALESCE(SUM(i.valor), 0) FROM Ingreso i")
    BigDecimal sumaTotalIngresos();
}