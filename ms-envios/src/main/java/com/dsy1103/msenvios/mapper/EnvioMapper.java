package com.dsy1103.msenvios.mapper;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.dto.EnvioDTO;
import org.springframework.stereotype.Component;

@Component // Spring sabe que es un componente inyectable
public class EnvioMapper {

    // 1. Convertir de MODELO a RESPONSE DTO (Salida)
    public EnvioDTO toDTO(EnvioModelo modelo) {
        if (modelo == null) return null;

        return EnvioDTO.builder()
                .id(modelo.getId()) // Pasamos el ID porque ya existe en la BD
                .codigoEnvio(modelo.getCodigoEnvio())
                .pedidoId(modelo.getPedidoId())
                .usuarioId(modelo.getUsuarioId())
                .direccionDestino(modelo.getDireccionDestino())
                .estadoEnvio(modelo.getEstadoEnvio())
                .fechaSalida(modelo.getFechaSalida())
                .fechaEntregaEstimada(modelo.getFechaEntregaEstimada())
                .fechaEntregado(modelo.getFechaEntregado())
                .activo(modelo.getActivo())
                .build(); // Cierra y construye el objeto de salida
    }

    // 2. Convertir de REQUEST DTO a MODELO (Para guardar)
    public EnvioModelo toEntity(EnvioDTO dto) {
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
                .build(); // Cierra y construye el modelo listo para el repository.save()
    }
}

