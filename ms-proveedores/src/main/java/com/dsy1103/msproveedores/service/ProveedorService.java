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

    public List<ProveedorDTO> listarProveedores() {
        log.info("Listando todos los PROVEEDORES");

        return proveedorRepository.findAll()
                .stream()
                .map(ProveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProveedorDTO obtenerProveedorPorId(Long id) {
        log.info("Obteniendo PROVEEDOR por ID {}", id);
        ProveedorDTO pDTO = proveedorRepository.findById(id)
                .map(ProveedorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El PROVEEDOR con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));

        pDTO.setListaContrato(contratoRepository.findByProveedorId(id)
                .stream()
                .map(ContratoMapper::toDTO)
                .collect(Collectors.toList()));

        return pDTO;
    }

    public List<ProveedorDTO> litarProveedoresActivos() {
        log.info("Obteniendo PROVEEDORES ACTIVOS");
        return proveedorRepository.findAllByActivo()
                .stream()
                .map(ProveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProveedorDTO guardarProveedor(ProveedorDTO pDTO) {
        log.info("Intentando registrar PROVEEDOR con ID {}", pDTO.getId());
        ProveedorModel pModel = ProveedorMapper.toEntity(pDTO);

        ProveedorModel guardado = proveedorRepository.save(pModel);
        log.info("PROVEEDOR guardado exitosamente con ID: {}", guardado.getId());

        return ProveedorMapper.toDTO(guardado);
    }

    public void actualizarProveedor(ProveedorDTO pDTO) {
        log.info("Actualizando PROVEEDOR con ID {}", pDTO.getId());

        proveedorRepository.findById(pDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PROVEEDOR no encontrado."));

        proveedorRepository.save(ProveedorModel.builder()
                .id(pDTO.getId())
                .nombre(pDTO.getNombre())
                .razonSocial(pDTO.getRazonSocial())
                .documentoFiscal(pDTO.getDocumentoFiscal())
                .correoContacto(pDTO.getCorreoContacto())
                .ciudad(pDTO.getCiudad())
                .calificacion(pDTO.getCalificacion())
                .activo(pDTO.getActivo())
                .fechaRegistro(pDTO.getFechaRegistro())
                .build());
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
