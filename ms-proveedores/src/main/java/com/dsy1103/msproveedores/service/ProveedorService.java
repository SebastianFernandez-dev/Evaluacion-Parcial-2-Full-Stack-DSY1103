package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ProveedorDTO;
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

    public List<ProveedorDTO> listarProveedores() {
        log.info("Listando todos los PROVEEDORES");

        return proveedorRepository.findAll()
                .stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProveedorDTO obtenerProveedorPorId(Long id) {
        log.info("Obteniendo PROVEEDOR por ID {}", id);
        ProveedorDTO pDTO = proveedorRepository.findById(id)
                .map(proveedorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El PROVEEDOR con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));

        pDTO.setListaContrato(contratoRepository.findByProveedorId(id)
                .stream()
                .map(contratoMapper::toDTO)
                .collect(Collectors.toList()));

        return pDTO;
    }

    public ProveedorDTO guardarProveedor(ProveedorDTO pDTO) {
        log.info("Intentando registrar PROVEEDOR con ID {}", pDTO.getId());
        ProveedorModel pModel = proveedorMapper.toEntity(pDTO);

        ProveedorModel guardado = proveedorRepository.save(pModel);
        log.info("PROVEEDOR guardado exitosamente con ID: {}", guardado.getId());

        return proveedorMapper.toDTO(guardado);
    }

    public ProveedorDTO actualizarProveedor(ProveedorDTO pDTO) {
        log.info("Actualizando PROVEEDOR con ID {}", pDTO.getId());

        ProveedorModel pExistente = proveedorRepository.findById(pDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: El PROVEEDOR no encontrado."));

        pExistente.setNombre(pDTO.getNombre());
        pExistente.setRazonSocial(pDTO.getRazonSocial());
        pExistente.setDocumentoFiscal(pDTO.getDocumentoFiscal());
        pExistente.setCorreoContacto(pDTO.getCorreoContacto());
        pExistente.setCiudad(pDTO.getCiudad());
        pExistente.setCalificacion(pDTO.getCalificacion());
        pExistente.setActivo(pDTO.getActivo());
        pExistente.setFechaRegistro(pDTO.getFechaRegistro());

        ProveedorModel actualizado = proveedorRepository.save(pExistente);

        return proveedorMapper.toDTO(actualizado);
    }

    public void eliminarProveedor(Long id) {
        log.warn("Eliminando PROVEEDOR con ID: {}", id);

        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: PROVEEDOR no encontrado.");
        }

        proveedorRepository.deleteById(id);
        log.info("PROVEEDOR eliminado exitosamente con ID: {}", id);
    }
}
