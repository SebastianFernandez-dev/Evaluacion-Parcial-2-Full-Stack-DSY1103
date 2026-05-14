package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ContratoDTO;
import com.dsy1103.msproveedores.mapper.ContratoMapper;
import com.dsy1103.msproveedores.model.ContratoModel;
import com.dsy1103.msproveedores.model.ProveedorModel;
import com.dsy1103.msproveedores.repository.ContratoRepository;
import com.dsy1103.msproveedores.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ContratoMapper contratoMapper;


    public List<ContratoDTO> listarContratos() {
        log.info("Listando todos los contratos");
        return contratoRepository.findAll()
                .stream()
                .map(contratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContratoDTO obtenerContratoPorId(Long id) {
        log.info("Obteniendo contrato por id {}", id);
        return contratoRepository.findById(id)
                .map(contratoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El contrato con ID "
                + id + " no existe. No se pudo realizar la busqueda."));
    }

    public ContratoDTO guardarContrato(ContratoDTO dto) {
        log.info("Intentando registrar contrato código: {}", dto.getNumero());
        ContratoModel contrato = contratoMapper.toEntity(dto);

        ProveedorModel proveedor = proveedorRepository.findById(dto.getProveedorId()).orElseThrow(() -> new EntityNotFoundException("Error: El proveedor con id "
        + dto.getProveedorId() + " no existe. No se puede crear el contrato."));

        contrato.setProveedor(proveedor);
        ContratoModel guardado = contratoRepository.save(contrato);
        log.info("Contrato guardado exitosamente con id: {}", guardado.getId());
        return contratoMapper.toDTO(guardado);
    }

    public ContratoDTO actualizarContrato(ContratoDTO dto) {
        log.info("Actualizando contrato con id: {}", dto.getId());

        ContratoModel cExistente = contratoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: Contrato no encontrado."));

        cExistente.setNumero(dto.getNumero());
        cExistente.setTipo(dto.getTipo());
        cExistente.setValor(dto.getValor());
        cExistente.setFechaInicio(dto.getFechaInicio());
        cExistente.setFechaFin(dto.getFechaFin());
        cExistente.setVigente(dto.getVigente());
        cExistente.setObservaciones(dto.getObservaciones());

        if (dto.getProveedorId() != null) {
            ProveedorModel p = proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: Proveedor no encontrado."));
            cExistente.setProveedor(p);
        }

        ContratoModel actualizado = contratoRepository.save(cExistente);

        return contratoMapper.toDTO(actualizado);
    }

    public void eliminarContrato(Long id) {
        log.warn("Eliminando contrato con id: {}", id);

        if (!contratoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: Contrato no encontrado.");
        }

        contratoRepository.deleteById(id);
        log.info("Contrato eliminado exitosamente con id: {}", id);
    }
}
