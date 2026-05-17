package com.dsy1103.msempleados.service;

import com.dsy1103.msempleados.client.SucursalClient;
import com.dsy1103.msempleados.dto.EmpleadoDTO;
import com.dsy1103.msempleados.mapper.EmpleadoMapper;
import com.dsy1103.msempleados.model.EmpleadoModel;
import com.dsy1103.msempleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    public List<EmpleadoDTO> listarEmpleados() {
        log.info("Listando todos lo EMPLEADOS");

        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO obtenerEmpleadoPorID(Long id) {
        log.info("Obteniendo EMPLEADO por ID {}", id);
        return empleadoRepository.findById(id)
                .map(empleadoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El EMPLEADO con ID "
                        +id+ " no pudo ser encontrado"));
    }

    public EmpleadoDTO guardarEmpleado(EmpleadoDTO eDTO) {
        log.info("Registrando EMPLEADO con ID {}", eDTO.getId());

        sucursalClient.obtenerSucursalPorId(eDTO.getSucursalId());

        EmpleadoModel eModel = empleadoMapper.toEntity(eDTO);
        EmpleadoModel guardado = empleadoRepository.save(eModel);

        log.info("EMPLEADO guardado exitosamente con ID: {}", guardado.getId());
        return empleadoMapper.toDTO(guardado);
    }

    public EmpleadoDTO actualizarEmpleado(EmpleadoDTO eDTO) {
        log.info("Actualizando EMPLEADO con ID {}", eDTO.getId());

        EmpleadoModel existente = empleadoRepository.findById(eDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: EMPLEADO no encontrado"));

        sucursalClient.obtenerSucursalPorId(eDTO.getSucursalId());

        existente.setPrimerNombre(eDTO.getPrimerNombre());
        existente.setSegundoNombre(eDTO.getSegundoNombre());
        existente.setPrimerApellido(eDTO.getPrimerApellido());
        existente.setSegundoApellido(eDTO.getSegundoApellido());
        existente.setCargo(eDTO.getCargo());
        existente.setRut(eDTO.getRut());
        existente.setDvRut(eDTO.getDvRut());
        existente.setCorreoEmpleado(eDTO.getCorreoEmpleado());
        existente.setFechaIngreso(eDTO.getFechaIngreso());
        existente.setActivoEmpleado(eDTO.getActivoEmpleado());
        existente.setSucursalId(eDTO.getSucursalId());

        EmpleadoModel actualizado = empleadoRepository.save(existente);
        return empleadoMapper.toDTO(actualizado);
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