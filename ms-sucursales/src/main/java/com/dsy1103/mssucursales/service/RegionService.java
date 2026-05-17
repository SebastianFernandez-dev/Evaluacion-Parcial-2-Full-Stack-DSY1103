package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.RegionDTO;
import com.dsy1103.mssucursales.mapper.RegionMapper;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> listarRegiones() {
        log.info("Listando todas las REGIONES");

        return regionRepository.findAll()
                .stream()
                .map(RegionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RegionDTO obtenerRegionPorId(Long id) {
        log.info("Obteniendo REGION por ID {}", id);
        return regionRepository.findById(id)
                .map(RegionMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: La REGION con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));
    }

    public RegionDTO guardarProveedor(RegionDTO rDTO) {
        log.info("Intentando registrar REGION con ID {}", rDTO.getId());
        RegionModel pModel = RegionMapper.toEntity(rDTO);

        RegionModel guardado = regionRepository.save(pModel);
        log.info("REGION guardada exitosamente con ID: {}", guardado.getId());

        return RegionMapper.toDTO(guardado);
    }

    public void actualizarRegion(RegionDTO rDTO) {
        log.info("Actualizando REGION con ID {}", rDTO.getId());

        regionRepository.findById(rDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: REGION no encontrada."));

        regionRepository.save(RegionModel.builder()
                .id(rDTO.getId())
                .nombre(rDTO.getNombre())
                .codigo(rDTO.getCodigo())
                .descripcion(rDTO.getDescripcion())
                .pais(rDTO.getPais())
                .fechaCreacion(rDTO.getFechaCreacion())
                .build());
    }

    public void eliminarRegion(Long id) {
        log.warn("Eliminando REGION con ID {}", id);

        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: REGION no encontrada.");
        }

        regionRepository.deleteById(id);
        log.info("REGION eliminada exitosamente con ID: {}", id);
    }
}
