package com.dsy1103.msproveedores.mapper;

import com.dsy1103.msproveedores.dto.ProveedorDTO;
import com.dsy1103.msproveedores.model.ContratoModel;
import com.dsy1103.msproveedores.model.ProveedorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorMapper {

    @Autowired
    private ContratoMapper contratoMapper;

    public ProveedorDTO toDTO(ProveedorModel pModel) {
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

    public ProveedorModel toEntity(ProveedorDTO pDTO) {
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
