package com.IngSw.GFH.repository;

import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);

    List<Empleado> findByActivoTrue();

    List<Empleado> findByRolAndActivoTrue(Rol rol);
}