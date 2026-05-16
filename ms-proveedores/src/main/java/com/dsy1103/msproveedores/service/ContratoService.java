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


    public List<ContratoDTO> listarContratos() {
        log.info("Listando todos los CONTRATOS");

        return contratoRepository.findAll()
                .stream()
                .map(ContratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContratoDTO obtenerContratoPorId(Long id) {
        log.info("Obteniendo CONTRATO por ID {}", id);

        return contratoRepository.findById(id)
                .map(ContratoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El CONTRATO con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));
    }

    public List<ContratoDTO> listarContratosPorProveedor(Long pId) {
        log.info("Listando contratos por ID del PROVEEDOR {}", pId);

        return contratoRepository.findByProveedorId(pId)
                .stream()
                .map(ContratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContratoDTO guardarContrato(ContratoDTO dto) {
        log.info("Intentando registrar CONTRATO CÓDIGO: {}", dto.getNumero());
        ContratoModel contrato = ContratoMapper.toEntity(dto);

        ProveedorModel proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new EntityNotFoundException("Error: El proveedor con id "
        + dto.getProveedorId() + " no existe. No se puede registrar el CONTRATO."));

        contrato.setProveedor(proveedor);
        ContratoModel guardado = contratoRepository.save(contrato);
        log.info("CONTRATO guardado exitosamente con ID: {}", guardado.getId());

        return ContratoMapper.toDTO(guardado);
    }

    public ContratoDTO actualizarContrato(ContratoDTO dto) {
        log.info("Actualizando CONTRATO con ID: {}", dto.getId());

        ContratoModel cExistente = contratoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: CONTRATO no encontrado."));

        cExistente.setNumero(dto.getNumero());
        cExistente.setTipo(dto.getTipo());
        cExistente.setValor(dto.getValor());
        cExistente.setFechaInicio(dto.getFechaInicio());
        cExistente.setFechaFin(dto.getFechaFin());
        cExistente.setVigente(dto.getVigente());
        cExistente.setObservaciones(dto.getObservaciones());

        if (dto.getProveedorId() != null) {
            ProveedorModel p = proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR no encontrado."));
            cExistente.setProveedor(p);
        }

        ContratoModel actualizado = contratoRepository.save(cExistente);

        return ContratoMapper.toDTO(actualizado);
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
