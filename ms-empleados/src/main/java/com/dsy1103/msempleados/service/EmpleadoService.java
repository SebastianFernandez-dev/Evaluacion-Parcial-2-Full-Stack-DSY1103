package com.dsy1103.msempleados.service;

import com.dsy1103.msempleados.client.SucursalClient;
import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.mapper.EmpleadoMapper;
import com.dsy1103.msempleados.model.EmpleadoModel;
import com.dsy1103.msempleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoMapper empleadoMapper;
    @Autowired
    private SucursalClient sucursalClient;

    public List<EmpleadoResponseDTO> listarEmpleados() {
        log.info("Listando todos lo EMPLEADOS");

        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO obtenerEmpleadoPorID(Long id) {
        log.info("Obteniendo EMPLEADO por ID {}", id);
        return empleadoRepository.findById(id)
                .map(empleadoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El EMPLEADO con ID "
                        +id+ " no pudo ser encontrado"));
    }

    public List<EmpleadoResponseDTO> listarPorSucursalYAnio(Long sucursalId, int anio) {
        log.info("Listando EMPLEADOS por sucursalId {} y anio {}", sucursalId, anio);

        sucursalClient.obtenerSucursalPorId(sucursalId);
        return empleadoRepository.findAllBySucursalAndAnio(sucursalId, anio)
                .stream()
                .map(empleadoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO guardarEmpleado(EmpleadoRequestDTO eDTO) {
        log.info("Registrando EMPLEADO");

        sucursalClient.obtenerSucursalPorId(eDTO.getSucursalId());

        EmpleadoModel eModel = empleadoMapper.toEntity(eDTO);
        EmpleadoModel guardado = empleadoRepository.save(eModel);

        log.info("EMPLEADO guardado exitosamente con ID: {}", guardado.getId());
        return empleadoMapper.toResponseDTO(guardado);
    }

    public EmpleadoResponseDTO actualizarEmpleado(Long id, EmpleadoRequestDTO eDTO) {
        log.info("Actualizando EMPLEADO con ID {}", id);

        EmpleadoModel existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: EMPLEADO no encontrado"));

        sucursalClient.obtenerSucursalPorId(eDTO.getSucursalId());

        empleadoMapper.updateEntity(eDTO, existente);

        EmpleadoModel actualizado = empleadoRepository.save(existente);
        return empleadoMapper.toResponseDTO(actualizado);
    }

    public void eliminarEmpleado(Long id) {
        log.warn("Eliminando EMPLEADO con ID: {}", id);

        if(!empleadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: EMPLEADO no encontrado");
        }

        empleadoRepository.deleteById(id);
        log.info("EMPLEADO eliminado exitosamente con ID: {}", id);
    }

}