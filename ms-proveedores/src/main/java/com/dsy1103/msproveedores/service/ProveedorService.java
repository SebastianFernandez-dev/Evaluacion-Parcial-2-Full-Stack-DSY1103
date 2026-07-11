package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.exception.DocumentoFiscalAlreadyExistsException;
import com.dsy1103.msproveedores.mapper.ContratoMapper;
import com.dsy1103.msproveedores.mapper.ProveedorMapper;
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
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ProveedorMapper proveedorMapper;
    @Autowired
    private ContratoMapper contratoMapper;

    public List<ProveedorResponseDTO> listarProveedores() {
        log.info("Listando todos los PROVEEDORES");

        return proveedorRepository.findAll()
                .stream()
                .map(proveedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProveedorResponseDTO obtenerProveedorPorId(Long id) {
        log.info("Obteniendo PROVEEDOR por ID {}", id);
        ProveedorResponseDTO pDTO = proveedorRepository.findById(id)
                .map(proveedorMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El PROVEEDOR con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));

        pDTO.setListaContrato(contratoRepository.findByProveedorId(id)
                .stream()
                .map(contratoMapper::toResponseDTO)
                .collect(Collectors.toList()));

        return pDTO;
    }

    public List<ProveedorResponseDTO> listarProveedoresActivos() {
        log.info("Obteniendo PROVEEDORES ACTIVOS");
        return proveedorRepository.findAllByActivo()
                .stream()
                .map(proveedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProveedorResponseDTO guardarProveedor(ProveedorRequestDTO dto) {
        log.info("Intentando registrar PROVEEDOR: {}", dto.getNombre());

        validarDocumentoFiscalUnico(dto.getDocumentoFiscal(), null);

        ProveedorModel model = proveedorMapper.toEntity(dto);
        ProveedorModel guardado = proveedorRepository.save(model);
        log.info("PROVEEDOR guardado exitosamente con ID: {}", guardado.getId());

        return proveedorMapper.toResponseDTO(guardado);
    }

    public ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto) {
        log.info("Actualizando PROVEEDOR con ID {}", id);

        ProveedorModel existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR no encontrado."));

        validarDocumentoFiscalUnico(dto.getDocumentoFiscal(), id);

        existente.setNombre(dto.getNombre());
        existente.setRazonSocial(dto.getRazonSocial());
        existente.setDocumentoFiscal(dto.getDocumentoFiscal());
        existente.setCorreoContacto(dto.getCorreoContacto());
        existente.setCiudad(dto.getCiudad());
        existente.setCalificacion(dto.getCalificacion());
        existente.setActivo(dto.getActivo());
        existente.setFechaRegistro(dto.getFechaRegistro());

        ProveedorModel actualizado = proveedorRepository.save(existente);
        log.info("PROVEEDOR actualizado exitosamente con ID: {}", actualizado.getId());

        return proveedorMapper.toResponseDTO(actualizado);
    }

    public void eliminarProveedor(Long id) {
        log.warn("Eliminando PROVEEDOR con ID: {}", id);

        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: PROVEEDOR no encontrado.");
        }

        proveedorRepository.deleteById(id);
        log.info("PROVEEDOR eliminado exitosamente con ID: {}", id);
    }

    private void validarDocumentoFiscalUnico(String documentoFiscal, Long idExcluir) {
        proveedorRepository.findByDocumentoFiscal(documentoFiscal).ifPresent(p -> {
            if (idExcluir == null || !p.getId().equals(idExcluir)) {
                throw new DocumentoFiscalAlreadyExistsException(
                        "Error: El DOCUMENTO FISCAL '" + documentoFiscal + "' ya está registrado en otro proveedor");
            }
        });
    }
}
