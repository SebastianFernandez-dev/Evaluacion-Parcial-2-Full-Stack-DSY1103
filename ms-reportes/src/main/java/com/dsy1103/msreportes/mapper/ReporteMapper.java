package com.dsy1103.msreportes.mapper;

import com.dsy1103.msreportes.dto.ReporteDTO;
import com.dsy1103.msreportes.model.ReporteModel;

public class ReporteMapper {

    public static ReporteDTO toDTO(ReporteModel rModel) {
        if (rModel == null) return null;

        return ReporteDTO.builder()
                .id(rModel.getId())
                .descripcion(rModel.getDescripcion())
                .tipo(rModel.getTipo())
                .totalVentas(rModel.getTotalVentas())
                .cantidadPedidos(rModel.getCantidadPedidos())
                .cantidadPagos(rModel.getCantidadPagos())
                .fechaGeneracion(rModel.getFechaGeneracion())
                .publicado(rModel.getPublicado())
                .usuarioId(rModel.getUsuarioId())
                .build();
    }

    public static ReporteModel toEntity(ReporteDTO rDTO) {
        if (rDTO == null) return null;

        return ReporteModel.builder()
                .descripcion(rDTO.getDescripcion())
                .tipo(rDTO.getTipo())
                .totalVentas(rDTO.getTotalVentas())
                .cantidadPedidos(rDTO.getCantidadPedidos())
                .cantidadPagos(rDTO.getCantidadPagos())
                .fechaGeneracion(rDTO.getFechaGeneracion())
                .publicado(rDTO.getPublicado())
                .usuarioId(rDTO.getUsuarioId())
                .build();
    }
}
