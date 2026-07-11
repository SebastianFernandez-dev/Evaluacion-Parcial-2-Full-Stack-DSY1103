package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
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
    @Autowired
    private RegionMapper regionMapper;

    public List<RegionResponseDTO> listarRegiones() {
        log.info("Listando todas las REGIONES");
        return regionRepository.findAll()
                .stream()
                .map(regionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public RegionResponseDTO obtenerRegionPorId(Long id) {
        log.info("Obteniendo REGION por ID {}", id);
        return regionRepository.findById(id)
                .map(regionMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: La REGION con ID " + id + " no pudo ser encontrada"));
    }

    public RegionResponseDTO guardarRegion(RegionRequestDTO dto) {
        log.info("Registrando REGION: {}", dto.getNombre());
        RegionModel model = regionMapper.toEntity(dto);
        RegionModel guardado = regionRepository.save(model);
        log.info("REGION guardada exitosamente con ID: {}", guardado.getId());
        return regionMapper.toResponseDTO(guardado);
    }

    public RegionResponseDTO actualizarRegion(Long id, RegionRequestDTO dto) {
        log.info("Actualizando REGION con ID {}", id);

        RegionModel existente = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: REGION no encontrada"));

        existente.setNombre(dto.getNombre());
        existente.setCodigo(dto.getCodigo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPais(dto.getPais());
        existente.setFechaCreacion(dto.getFechaCreacion());

        RegionModel actualizado = regionRepository.save(existente);
        return regionMapper.toResponseDTO(actualizado);
    }

    public void eliminarRegion(Long id) {
        log.warn("Eliminando REGION con ID {}", id);
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: REGION no encontrada");
        }
        regionRepository.deleteById(id);
        log.info("REGION eliminada exitosamente con ID: {}", id);
    }
}
