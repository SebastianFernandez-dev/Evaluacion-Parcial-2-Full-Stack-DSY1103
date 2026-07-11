package com.dsy1103.msreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponseDTO {

    private Long id;
    private String descripcion;
    private String tipo;
    private Double totalVentas;
    private Integer cantidadPedidos;
    private Integer cantidadPagos;
    private LocalDate fechaGeneracion;
    private Boolean publicado;
    private Long usuarioId;
}
