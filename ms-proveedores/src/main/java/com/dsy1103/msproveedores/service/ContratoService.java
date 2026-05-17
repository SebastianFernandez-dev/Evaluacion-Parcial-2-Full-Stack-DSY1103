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

    public ContratoDTO guardarContrato(ContratoDTO cDTO) {
        log.info("Intentando registrar CONTRATO CÓDIGO: {}", cDTO.getNumero());
        ContratoModel contrato = ContratoMapper.toEntity(cDTO);

        ProveedorModel proveedor = proveedorRepository.findById(cDTO.getProveedorId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR con ID "
        + cDTO.getProveedorId() + " no existe. No se puede registrar el CONTRATO."));

        contrato.setProveedor(proveedor);
        ContratoModel guardado = contratoRepository.save(contrato);
        log.info("CONTRATO guardado exitosamente con ID: {}", guardado.getId());

        return ContratoMapper.toDTO(guardado);
    }

    public void actualizarContrato(ContratoDTO cDTO) {
        log.info("Actualizando CONTRATO con ID: {}", cDTO.getId());

        ContratoModel cExistente = contratoRepository.findById(cDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: CONTRATO no encontrado."));

        if (cDTO.getProveedorId() != null) {
            ProveedorModel p = proveedorRepository.findById(cDTO.getProveedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR no encontrado."));
            cExistente.setProveedor(p);
        }

        contratoRepository.save(ContratoModel.builder()
                .id(cDTO.getId())
                .numero(cDTO.getNumero())
                .tipo(cDTO.getTipo())
                .valor(cDTO.getValor())
                .fechaInicio(cDTO.getFechaInicio())
                .fechaFin(cDTO.getFechaFin())
                .vigente(cDTO.getVigente())
                .observaciones(cDTO.getObservaciones())
                .proveedor(cExistente.getProveedor())
                .build());
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
