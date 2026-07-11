package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.mapper.SucursalMapper;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.model.SucursalModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import com.dsy1103.mssucursales.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SucursalMapper sucursalMapper;

    public List<SucursalResponseDTO> listarSucursales() {
        log.info("Listando todas las SUCURSALES");
        return sucursalRepository.findAll()
                .stream()
                .map(sucursalMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SucursalResponseDTO obtenerSucursalPorId(Long id) {
        log.info("Obteniendo SUCURSAL por ID {}", id);
        return sucursalRepository.findById(id)
                .map(sucursalMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: La SUCURSAL con ID " + id + " no pudo ser encontrada"));
    }

    public List<SucursalResponseDTO> listarSucursalesPorRegion(String nombre) {
        log.info("Listando SUCURSALES de la REGION {}", nombre);
        return sucursalRepository.findAllByRegionNombre(nombre)
                .stream()
                .map(sucursalMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SucursalResponseDTO guardarSucursal(SucursalRequestDTO dto) {
        log.info("Registrando SUCURSAL: {}", dto.getNombre());

        RegionModel region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: La REGION con ID " + dto.getRegionId() + " no existe"));

        SucursalModel model = sucursalMapper.toEntity(dto, region);
        SucursalModel guardado = sucursalRepository.save(model);
        log.info("SUCURSAL guardada exitosamente con ID: {}", guardado.getId());
        return sucursalMapper.toResponseDTO(guardado);
    }

    public SucursalResponseDTO actualizarSucursal(Long id, SucursalRequestDTO dto) {
        log.info("Actualizando SUCURSAL con ID {}", id);

        SucursalModel existente = sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: SUCURSAL no encontrada"));

        existente.setNombre(dto.getNombre());
        existente.setCodigo(dto.getCodigo());
        existente.setDireccion(dto.getDireccion());
        existente.setCapacidadAtencion(dto.getCapacidadAtencion());
        existente.setActivo(dto.getActivo());
        existente.setFechaApertura(dto.getFechaApertura());

        if (dto.getRegionId() != null) {
            RegionModel region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: REGION no encontrada"));
            existente.setRegion(region);
        }

        SucursalModel actualizado = sucursalRepository.save(existente);
        return sucursalMapper.toResponseDTO(actualizado);
    }

    public void eliminarSucursal(Long id) {
        log.warn("Eliminando SUCURSAL con ID {}", id);
        if (!sucursalRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: SUCURSAL no encontrada");
        }
        sucursalRepository.deleteById(id);
        log.info("SUCURSAL eliminada exitosamente con ID: {}", id);
    }
}
