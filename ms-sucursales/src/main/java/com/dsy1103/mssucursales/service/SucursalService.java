package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.SucursalDTO;
import com.dsy1103.mssucursales.mapper.SucursalMapper;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.model.SucursalModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import com.dsy1103.mssucursales.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private RegionRepository regionRepository;

    public List<SucursalDTO> listarSucursales(){
        log.info("Listando todas las SUCURSALES");

        return sucursalRepository.findAll()
                .stream()
                .map(SucursalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SucursalDTO obtenerSucursalPorId(Long id){
        log.info("Obteniendo SUCURSAL por ID {}", id);

        return sucursalRepository.findById(id)
                .map(SucursalMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: SUCURSAL con ID"
                + id + " no existe. No se pudo realizar la busqueda"));
    }

    public List<SucursalDTO> listarSucursalesPorRegion(String nombre) {
        log.info("Listando SUCURSALES de la REGION {}", nombre);

        return sucursalRepository.findAllByRegionNombre(nombre)
                .stream()
                .map(SucursalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SucursalDTO guardarSucursal(SucursalDTO sDTO) {
        log.info("Intentado registrar SUCURSAL ID {}", sDTO.getId());
        SucursalModel sucursal = SucursalMapper.toEntity(sDTO);

        RegionModel region = regionRepository.findById(sDTO.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Error: REGION con ID "
                        + sDTO.getRegionId() + " no existe. No se puede registrar la SUCURSAL."));

        sucursal.setRegion(region);
        SucursalModel guardado = sucursalRepository.save(sucursal);
        log.info("SUCURSAL guardada exitosamente con ID: {}", guardado.getId());

        return SucursalMapper.toDTO(guardado);
    }

    public void actualizarSucursal(SucursalDTO sDTO) {
        log.info("Actualizando SUCURSAL con ID {}", sDTO.getId());

        SucursalModel sExistente = sucursalRepository.findById(sDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: SUCURSAL no encontrada."));

        if (sDTO.getRegionId() != null) {
            RegionModel r  = regionRepository.findById(sDTO.getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: REGION no encontrada."));
            sExistente.setRegion(r);
        }

        sucursalRepository.save(SucursalModel.builder()
                .id(sDTO.getId())
                .nombre(sDTO.getNombre())
                .codigo(sDTO.getCodigo())
                .direccion(sDTO.getDireccion())
                .capacidadAtencion(sDTO.getCapacidadAtencion())
                .activo(sDTO.getActivo())
                .fechaApertura(sDTO.getFechaApertura())
                .region(sExistente.getRegion())
                .build());
    }

    public void eliminarSucursal(Long id) {
        log.warn("Eliminando SUCURSAL con ID {}", id);

        if (!sucursalRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: SUCURSAL no encontrada.");
        }

        sucursalRepository.deleteById(id);
        log.info("SUCURSAL eliminada exitosamente con ID: {}", id);
    }
}
