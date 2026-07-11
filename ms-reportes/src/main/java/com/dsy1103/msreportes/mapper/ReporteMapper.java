package com.dsy1103.msreportes.mapper;

import com.dsy1103.msreportes.dto.ReporteRequestDTO;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import com.dsy1103.msreportes.model.ReporteModel;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteResponseDTO toResponseDTO(ReporteModel model) {
        if (model == null) return null;

        return ReporteResponseDTO.builder()
                .id(model.getId())
                .descripcion(model.getDescripcion())
                .tipo(model.getTipo())
                .totalVentas(model.getTotalVentas())
                .cantidadPedidos(model.getCantidadPedidos())
                .cantidadPagos(model.getCantidadPagos())
                .fechaGeneracion(model.getFechaGeneracion())
                .publicado(model.getPublicado())
                .usuarioId(model.getUsuarioId())
                .build();
    }

    public ReporteModel toEntity(ReporteRequestDTO dto) {
        if (dto == null) return null;

        return ReporteModel.builder()
                .descripcion(dto.getDescripcion())
                .tipo(dto.getTipo())
                .totalVentas(dto.getTotalVentas())
                .cantidadPedidos(dto.getCantidadPedidos())
                .cantidadPagos(dto.getCantidadPagos())
                .publicado(dto.getPublicado())
                .usuarioId(dto.getUsuarioId())
                .build();
    }
}
