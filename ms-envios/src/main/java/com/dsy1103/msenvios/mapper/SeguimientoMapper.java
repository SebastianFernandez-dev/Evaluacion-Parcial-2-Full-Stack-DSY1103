package com.dsy1103.msenvios.mapper;

import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import org.springframework.stereotype.Component;

@Component
public class SeguimientoMapper {

    public SeguimientoResponseDTO toResponseDTO(SeguimientoModelo modelo) {
        if (modelo == null) return null;

        return SeguimientoResponseDTO.builder()
                .id(modelo.getId())
                .estadoSegui(modelo.getEstadoSegui())
                .ubiAtual(modelo.getUbiAtual())
                .observacion(modelo.getObservacion())
                .fechaSegui(modelo.getFechaSegui())
                .visible(modelo.getVisible())
                .envioId(modelo.getEnvio() != null ? modelo.getEnvio().getId() : null)
                .build();
    }

    public SeguimientoModelo toEntity(SeguimientoRequestDTO dto) {
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
