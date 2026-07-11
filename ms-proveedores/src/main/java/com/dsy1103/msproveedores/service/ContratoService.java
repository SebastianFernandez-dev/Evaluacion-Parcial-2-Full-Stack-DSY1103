package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
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

    public List<ContratoResponseDTO> listarContratos() {
        log.info("Listando todos los CONTRATOS");

        return contratoRepository.findAll()
                .stream()
                .map(contratoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ContratoResponseDTO obtenerContratoPorId(Long id) {
        log.info("Obteniendo CONTRATO por ID {}", id);

        return contratoRepository.findById(id)
                .map(contratoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El CONTRATO con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));
    }

    public List<ContratoResponseDTO> listarContratosPorProveedor(Long pId) {
        log.info("Listando CONTRATOS por ID del PROVEEDOR {}", pId);

        return contratoRepository.findByProveedorId(pId)
                .stream()
                .map(contratoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ContratoResponseDTO guardarContrato(ContratoRequestDTO dto) {
        log.info("Intentando registrar CONTRATO CÓDIGO: {}", dto.getNumero());
        ContratoModel contrato = contratoMapper.toEntity(dto);

        ProveedorModel proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR con ID "
                        + dto.getProveedorId() + " no existe. No se puede registrar el CONTRATO."));

        contrato.setProveedor(proveedor);
        ContratoModel guardado = contratoRepository.save(contrato);
        log.info("CONTRATO guardado exitosamente con ID: {}", guardado.getId());

        return contratoMapper.toResponseDTO(guardado);
    }

    public ContratoResponseDTO actualizarContrato(Long id, ContratoRequestDTO dto) {
        log.info("Actualizando CONTRATO con ID: {}", id);

        ContratoModel existente = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: CONTRATO no encontrado."));

        if (dto.getProveedorId() != null) {
            ProveedorModel p = proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR no encontrado."));
            existente.setProveedor(p);
        }

        existente.setNumero(dto.getNumero());
        existente.setTipo(dto.getTipo());
        existente.setValor(dto.getValor());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setVigente(dto.getVigente());
        existente.setObservaciones(dto.getObservaciones());

        ContratoModel actualizado = contratoRepository.save(existente);
        log.info("CONTRATO actualizado exitosamente con ID: {}", actualizado.getId());

        return contratoMapper.toResponseDTO(actualizado);
    }

    public void eliminarContrato(Long id) {
        log.warn("Eliminando CONTRATO con ID: {}", id);

        if (!contratoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: CONTRATO no encontrado.");
        }

        contratoRepository.deleteById(id);
        log.info("CONTRATO eliminado exitosamente con ID: {}", id);
    }
}
