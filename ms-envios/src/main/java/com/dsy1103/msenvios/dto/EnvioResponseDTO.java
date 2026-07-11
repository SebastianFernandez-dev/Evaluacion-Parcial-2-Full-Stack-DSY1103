package com.dsy1103.msenvios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvioResponseDTO {

    private Long id;
    private String codigoEnvio;
    private Long pedidoId;
    private Long usuarioId;
    private String direccionDestino;
    private String estadoEnvio;
    private LocalDateTime fechaSalida;
    private LocalDate fechaEntregaEstimada;
    private LocalDate fechaEntregado;
    private Boolean activo;
}
