package com.dsy1103.msenvios.mapper;

import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import org.springframework.stereotype.Component;

@Component
public class EnvioMapper {

    public EnvioResponseDTO toResponseDTO(EnvioModelo modelo) {
        if (modelo == null) return null;

        return EnvioResponseDTO.builder()
                .id(modelo.getId())
                .codigoEnvio(modelo.getCodigoEnvio())
                .pedidoId(modelo.getPedidoId())
                .usuarioId(modelo.getUsuarioId())
                .direccionDestino(modelo.getDireccionDestino())
                .estadoEnvio(modelo.getEstadoEnvio())
                .fechaSalida(modelo.getFechaSalida())
                .fechaEntregaEstimada(modelo.getFechaEntregaEstimada())
                .fechaEntregado(modelo.getFechaEntregado())
                .activo(modelo.getActivo())
                .build();
    }

    public EnvioModelo toEntity(EnvioRequestDTO dto) {
        if (dto == null) return null;

        return EnvioModelo.builder()
                .codigoEnvio(dto.getCodigoEnvio())
                .pedidoId(dto.getPedidoId())
                .usuarioId(dto.getUsuarioId())
                .direccionDestino(dto.getDireccionDestino())
                .estadoEnvio(dto.getEstadoEnvio())
                .fechaSalida(dto.getFechaSalida())
                .fechaEntregaEstimada(dto.getFechaEntregaEstimada())
                .fechaEntregado(dto.getFechaEntregado())
                .activo(dto.getActivo())
                .build();
    }
}
