package com.IngSw.GFH.repository;

import com.IngSw.GFH.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByDisponibleTrue();

    List<Mesa> findByMeseroIsNotNull();

    boolean existsByNumeroMesa(Integer numeroMesa);
}
