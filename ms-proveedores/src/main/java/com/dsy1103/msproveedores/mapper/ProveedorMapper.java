package com.dsy1103.msproveedores.mapper;

import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.model.ProveedorModel;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorResponseDTO toResponseDTO(ProveedorModel model) {
        if (model == null) return null;

        return ProveedorResponseDTO.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .razonSocial(model.getRazonSocial())
                .documentoFiscal(model.getDocumentoFiscal())
                .correoContacto(model.getCorreoContacto())
                .ciudad(model.getCiudad())
                .calificacion(model.getCalificacion())
                .activo(model.getActivo())
                .fechaRegistro(model.getFechaRegistro())
                .build();
    }

    public ProveedorModel toEntity(ProveedorRequestDTO dto) {
        if (dto == null) return null;

        return ProveedorModel.builder()
                .nombre(dto.getNombre())
                .razonSocial(dto.getRazonSocial())
                .documentoFiscal(dto.getDocumentoFiscal())
                .correoContacto(dto.getCorreoContacto())
                .ciudad(dto.getCiudad())
                .calificacion(dto.getCalificacion())
                .activo(dto.getActivo())
                .fechaRegistro(dto.getFechaRegistro())
                .build();
    }
}
