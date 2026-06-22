package com.dsy1103.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String direccion;
    private Integer capacidadAtencion;
    private Boolean activo;
    private LocalDate fechaApertura;
    private Long region;
}
