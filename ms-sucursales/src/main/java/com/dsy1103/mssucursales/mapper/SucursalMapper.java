package com.dsy1103.mssucursales.mapper;

import com.dsy1103.mssucursales.dto.SucursalDTO;
import com.dsy1103.mssucursales.model.SucursalModel;

public class SucursalMapper {
    
    public static SucursalDTO toDTO(SucursalModel sModel) {
        if (sModel == null) return null;
        
        return SucursalDTO.builder()
                .id(sModel.getId())
                .nombre(sModel.getNombre())
                .codigo(sModel.getCodigo())
                .direccion(sModel.getDireccion())
                .capacidadAtencion(sModel.getCapacidadAtencion())
                .activo(sModel.getActivo())
                .fechaApertura(sModel.getFechaApertura())
                .regionId(sModel.getRegion().getId())
                .build();
    }

    public static SucursalModel toEntity(SucursalDTO sDTO) {
        if (sDTO == null) return null;

        return SucursalModel.builder()
                .id(sDTO.getId())
                .nombre(sDTO.getNombre())
                .codigo(sDTO.getCodigo())
                .direccion(sDTO.getDireccion())
                .capacidadAtencion(sDTO.getCapacidadAtencion())
                .activo(sDTO.getActivo())
                .fechaApertura(sDTO.getFechaApertura())
                .build();
        //la region se agrega en el service, para consultar a la bdd si existe en realidad
    }
}
