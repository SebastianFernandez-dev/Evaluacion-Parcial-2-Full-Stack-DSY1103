package com.dsy1103.mssucursales.mapper;

import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import com.dsy1103.mssucursales.model.RegionModel;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionResponseDTO toResponseDTO(RegionModel model) {
        if (model == null) return null;

        return RegionResponseDTO.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .codigo(model.getCodigo())
                .descripcion(model.getDescripcion())
                .pais(model.getPais())
                .fechaCreacion(model.getFechaCreacion())
                .build();
    }

    public RegionModel toEntity(RegionRequestDTO dto) {
        if (dto == null) return null;

        return RegionModel.builder()
                .nombre(dto.getNombre())
                .codigo(dto.getCodigo())
                .descripcion(dto.getDescripcion())
                .pais(dto.getPais())
                .fechaCreacion(dto.getFechaCreacion())
                .build();
    }
}
