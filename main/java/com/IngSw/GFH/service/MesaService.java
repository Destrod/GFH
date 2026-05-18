package com.IngSw.GFH.service;

import com.IngSw.GFH.dto.AsignacionMesaRequest;
import com.IngSw.GFH.dto.MesaResponse;
import com.IngSw.GFH.exception.RecursoNoEncontradoException;
import com.IngSw.GFH.exception.OperacionInvalidaException;
import com.IngSw.GFH.model.Empleado;
import com.IngSw.GFH.model.Mesa;
import com.IngSw.GFH.model.Rol;
import com.IngSw.GFH.repository.EmpleadoRepository;
import com.IngSw.GFH.repository.MesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    private final EmpleadoRepository empleadoRepository;

    public MesaService(MesaRepository mesaRepository,
                       EmpleadoRepository empleadoRepository) {
        this.mesaRepository = mesaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<MesaResponse> consultarMesasDisponibles() {
        List<Mesa> mesas = mesaRepository.findByDisponibleTrue();
        if (mesas.size() == 0) {
            throw new RecursoNoEncontradoException("No hay mesas disponibles para asignar");
        }
        List<MesaResponse> resultado = new ArrayList<MesaResponse>();
        for (Mesa mesa : mesas) {
            resultado.add(MesaResponse.desde(mesa));
        }
        return resultado;
    }

    public List<MesaResponse> consultarMesasAsignadas() {
        List<Mesa> mesas = mesaRepository.findByMeseroIsNotNull();
        if (mesas.size() == 0) {
            throw new RecursoNoEncontradoException("No hay asignaciones disponibles para mostrar");
        }
        List<MesaResponse> resultado = new ArrayList<MesaResponse>();
        for (Mesa mesa : mesas) {
            resultado.add(MesaResponse.desde(mesa));
        }
        return resultado;
    }

    public List<MesaResponse> consultarTodasLasMesas() {
        List<Mesa> mesas = mesaRepository.findAll();
        List<MesaResponse> resultado = new ArrayList<MesaResponse>();
        for (Mesa mesa : mesas) {
            resultado.add(MesaResponse.desde(mesa));
        }
        return resultado;
    }

    @Transactional
    public MesaResponse asignarMesa(AsignacionMesaRequest request) {
        Optional<Mesa> mesaOpc = mesaRepository.findById(request.getMesaId());
        if (!mesaOpc.isPresent()) {
            throw new RecursoNoEncontradoException("Mesa con ID " + request.getMesaId() + " no encontrada");
        }
        Mesa mesa = mesaOpc.get();

        if (!mesa.isDisponible()) {
            throw new OperacionInvalidaException(
                    "La mesa " + mesa.getNumeroMesa() + " ya está asignada a otro mesero");
        }

        Optional<Empleado> meseroOpc = empleadoRepository.findById(request.getMeseroId());
        if (!meseroOpc.isPresent()) {
            throw new RecursoNoEncontradoException("Mesero con ID " + request.getMeseroId() + " no encontrado");
        }
        Empleado mesero = meseroOpc.get();

        if (mesero.getRol() != Rol.MESERO) {
            throw new OperacionInvalidaException("El empleado seleccionado no tiene el rol de MESERO");
        }

        mesa.asignarMesero(mesero);
        Mesa guardada = mesaRepository.save(mesa);
        return MesaResponse.desde(guardada);
    }

    @Transactional
    public MesaResponse liberarMesa(Long mesaId) {
        Optional<Mesa> mesaOpc = mesaRepository.findById(mesaId);
        if (!mesaOpc.isPresent()) {
            throw new RecursoNoEncontradoException("Mesa con ID " + mesaId + " no encontrada");
        }
        Mesa mesa = mesaOpc.get();

        if (mesa.isDisponible()) {
            throw new OperacionInvalidaException(
                    "La mesa " + mesa.getNumeroMesa() + " ya está disponible");
        }

        mesa.liberarMesa();
        Mesa guardada = mesaRepository.save(mesa);
        return MesaResponse.desde(guardada);
    }
}
