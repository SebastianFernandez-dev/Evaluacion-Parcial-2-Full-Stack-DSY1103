package com.dsy1103.msenvios.mapper;


import com.dsy1103.msenvios.dto.SeguimientoDTO;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class SeguimientoMapper {

    // 1. Convertir de MODELO a RESPONSE DTO (Salida)
    public SeguimientoDTO toDTO(SeguimientoModelo modelo) {
        if (modelo == null) return null;

        return SeguimientoDTO.builder()
                .id(modelo.getId()) // Pasamos el ID porque ya existe en la BD
                .estadoSegui(modelo.getEstadoSegui())
                .ubiAtual(modelo.getUbiAtual())
                .observacion(modelo.getObservacion())
                .fechaSegui(modelo.getFechaSegui())
                .envioId(modelo.getEnvio() != null ? modelo.getEnvio().getId() : null)
                .build();
    }

    // 2. Convertir de REQUEST DTO a MODELO (Para guardar)
    public SeguimientoModelo toEntity(SeguimientoDTO dto) {
        if (dto == null) return null;

        return SeguimientoModelo.builder()
                .estadoSegui(dto.getEstadoSegui())
                .ubiAtual(dto.getUbiAtual())
                .observacion(dto.getObservacion())
                .visible(dto.getVisible())
                .fechaSegui(dto.getFechaSegui())
                .build();
    }


}
