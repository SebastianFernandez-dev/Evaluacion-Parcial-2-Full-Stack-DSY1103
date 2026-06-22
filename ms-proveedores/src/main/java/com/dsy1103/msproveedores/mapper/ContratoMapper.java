package com.dsy1103.msproveedores.mapper;

import com.dsy1103.msproveedores.dto.ContratoDTO;
import com.dsy1103.msproveedores.model.ContratoModel;

public class ContratoMapper {

    public static ContratoDTO toDTO(ContratoModel cModel) {
        if (cModel == null) return null;

        return ContratoDTO.builder()
                .id(cModel.getId())
                .numero(cModel.getNumero())
                .tipo(cModel.getTipo())
                .valor(cModel.getValor())
                .fechaInicio(cModel.getFechaInicio())
                .fechaFin(cModel.getFechaFin())
                .vigente(cModel.getVigente())
                .observaciones(cModel.getObservaciones())
                .proveedorId(cModel.getProveedor().getId())
                .build();
    }

    public static ContratoModel toEntity(ContratoDTO cDTO) {
        if (cDTO == null) return null;

        return ContratoModel.builder()
                .numero(cDTO.getNumero())
                .tipo(cDTO.getTipo()).valor(cDTO.getValor())
                .fechaInicio(cDTO.getFechaInicio())
                .fechaFin(cDTO.getFechaFin())
                .vigente(cDTO.getVigente())
                .observaciones(cDTO.getObservaciones())
                .build();
        //el proveedor se agrega en el service, para consultar a la bdd si existe en realidad
    }
}
