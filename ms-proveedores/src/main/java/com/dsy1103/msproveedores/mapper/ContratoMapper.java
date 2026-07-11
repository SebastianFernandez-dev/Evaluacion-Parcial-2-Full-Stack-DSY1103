package com.dsy1103.msproveedores.mapper;

import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import com.dsy1103.msproveedores.model.ContratoModel;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {

    public ContratoResponseDTO toResponseDTO(ContratoModel model) {
        if (model == null) return null;

        return ContratoResponseDTO.builder()
                .id(model.getId())
                .numero(model.getNumero())
                .tipo(model.getTipo())
                .valor(model.getValor())
                .fechaInicio(model.getFechaInicio())
                .fechaFin(model.getFechaFin())
                .vigente(model.getVigente())
                .observaciones(model.getObservaciones())
                .proveedorId(model.getProveedor().getId())
                .build();
    }

    public ContratoModel toEntity(ContratoRequestDTO dto) {
        if (dto == null) return null;

        return ContratoModel.builder()
                .numero(dto.getNumero())
                .tipo(dto.getTipo())
                .valor(dto.getValor())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .vigente(dto.getVigente())
                .observaciones(dto.getObservaciones())
                .build();
    }
}
