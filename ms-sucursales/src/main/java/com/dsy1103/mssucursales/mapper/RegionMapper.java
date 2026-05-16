package com.dsy1103.mssucursales.mapper;

import com.dsy1103.mssucursales.dto.RegionDTO;
import com.dsy1103.mssucursales.model.RegionModel;

public class RegionMapper {

    public static RegionDTO toDTO(RegionModel rModel) {
        if (rModel == null) return null;

        return RegionDTO.builder()
                .id(rModel.getId())
                .nombre(rModel.getNombre())
                .codigo(rModel.getCodigo())
                .descripcion(rModel.getDescripcion())
                .fechaCreacion(rModel.getFechaCreacion())
                .build();
    }

    public static RegionModel toEntity(RegionDTO rDTO) {
        if (rDTO == null) return null;

        return RegionModel.builder()
                .id(rDTO.getId())
                .nombre(rDTO.getNombre())
                .codigo(rDTO.getCodigo())
                .descripcion(rDTO.getDescripcion())
                .fechaCreacion(rDTO.getFechaCreacion())
                .build();
    }
}
