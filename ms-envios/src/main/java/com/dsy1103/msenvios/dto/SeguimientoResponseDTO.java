package com.dsy1103.msenvios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientoResponseDTO {

    private Long id;
    private Long envioId;
    private String estadoSegui;
    private String ubiAtual;
    private String observacion;
    private LocalDateTime fechaSegui;
    private Boolean visible;
}
