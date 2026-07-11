package com.dsy1103.mssucursales.mapper;

import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.model.SucursalModel;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public SucursalResponseDTO toResponseDTO(SucursalModel model) {
        if (model == null) return null;

        return SucursalResponseDTO.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .codigo(model.getCodigo())
                .direccion(model.getDireccion())
                .capacidadAtencion(model.getCapacidadAtencion())
                .activo(model.getActivo())
                .fechaApertura(model.getFechaApertura())
                .regionId(model.getRegion() != null ? model.getRegion().getId() : null)
                .build();
    }

    public SucursalModel toEntity(SucursalRequestDTO dto, RegionModel region) {
        if (dto == null) return null;

        return SucursalModel.builder()
                .nombre(dto.getNombre())
                .codigo(dto.getCodigo())
                .direccion(dto.getDireccion())
                .capacidadAtencion(dto.getCapacidadAtencion())
                .activo(dto.getActivo())
                .fechaApertura(dto.getFechaApertura())
                .region(region)
                .build();
    }
}
