package com.dsy1103.mssucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SucursalResponseDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private String direccion;
    private Integer capacidadAtencion;
    private Boolean activo;
    private LocalDate fechaApertura;
    private Long regionId;
}
