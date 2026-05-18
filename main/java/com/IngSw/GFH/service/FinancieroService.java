package com.IngSw.GFH.service;

import com.IngSw.GFH.dto.EgresoRequest;
import com.IngSw.GFH.dto.InformeFinancieroResponse;
import com.IngSw.GFH.exception.RecursoNoEncontradoException;
import com.IngSw.GFH.model.Egreso;
import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.repository.EgresoRepository;
import com.IngSw.GFH.repository.EmpleadoRepository;
import com.IngSw.GFH.repository.IngresoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancieroService {

    private final EgresoRepository egresoRepository;
    private final IngresoRepository ingresoRepository;
    private final EmpleadoRepository empleadoRepository;

    public FinancieroService(EgresoRepository egresoRepository,
                             IngresoRepository ingresoRepository,
                             EmpleadoRepository empleadoRepository) {
        this.egresoRepository = egresoRepository;
        this.ingresoRepository = ingresoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional
    public void registrarEgreso(EgresoRequest request, String nombreUsuario) {
        Empleado admin = empleadoRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + nombreUsuario));

        Egreso egreso = new Egreso(
                request.getConcepto(),
                request.getValor(),
                request.getFecha(),
                admin
        );

        egresoRepository.save(egreso);
    }

    public InformeFinancieroResponse consultarInformeFinanciero() {
        BigDecimal totalIngresos = ingresoRepository.sumaTotalIngresos();
        BigDecimal totalEgresos  = egresoRepository.sumaTotalEgresos();

        // CU-08: Flujo alternativo — sin registros financieros
        if (totalIngresos.compareTo(BigDecimal.ZERO) == 0
                && totalEgresos.compareTo(BigDecimal.ZERO) == 0) {
            throw new RecursoNoEncontradoException(
                    "No hay datos registrados para mostrar");
        }

        return InformeFinancieroResponse.calcular(totalIngresos, totalEgresos);
    }

    public List<Egreso> consultarEgresos() {
        return egresoRepository.findAll();
    }
}
