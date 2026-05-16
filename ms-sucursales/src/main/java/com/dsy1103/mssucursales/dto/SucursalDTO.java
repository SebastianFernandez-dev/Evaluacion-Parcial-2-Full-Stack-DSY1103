package com.dsy1103.mssucursales.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SucursalDTO {

    @Positive
    private Long id;

    private String nombre;

    private String codigo;

    private String direccion;

    private Integer capacidadAtencion;

    private Boolean activo;

    private LocalDate fechaApertura;

    @Positive
    private Long regionId;
}
