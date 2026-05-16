package com.dsy1103.msproveedores.mapper;

import com.dsy1103.msproveedores.dto.ProveedorDTO;
import com.dsy1103.msproveedores.model.ProveedorModel;

public class ProveedorMapper {

    public static ProveedorDTO toDTO(ProveedorModel pModel) {
        if (pModel == null) return null;

        return ProveedorDTO.builder()
                .id(pModel.getId())
                .nombre(pModel.getNombre())
                .razonSocial(pModel.getRazonSocial())
                .documentoFiscal(pModel.getDocumentoFiscal())
                .correoContacto(pModel.getCorreoContacto())
                .ciudad(pModel.getCiudad())
                .calificacion(pModel.getCalificacion())
                .activo(pModel.getActivo())
                .fechaRegistro(pModel.getFechaRegistro())
                .build();
    }

    public static ProveedorModel toEntity(ProveedorDTO pDTO) {
        if (pDTO == null) return null;

        return ProveedorModel.builder()
                .nombre(pDTO.getNombre())
                .razonSocial(pDTO.getRazonSocial())
                .documentoFiscal(pDTO.getDocumentoFiscal())
                .correoContacto(pDTO.getCorreoContacto())
                .ciudad(pDTO.getCiudad())
                .calificacion(pDTO.getCalificacion())
                .activo(pDTO.getActivo())
                .fechaRegistro(pDTO.getFechaRegistro())
                .build();
    }
}
