package com.dsy1103.msproveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContratoResponseDTO {

    private Long id;
    private String numero;
    private String tipo;
    private Double valor;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean vigente;
    private String observaciones;
    private Long proveedorId;
}
