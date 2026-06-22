package com.dsy1103.mssucursales.dto;

import jakarta.validation.constraints.*;
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

    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message="El campo NOMBRE es obligatorio")
    @Size(max=80)
    private String nombre;

    @NotBlank(message="El campo CODIGO es obligatorio")
    @Size(min=2, max=20)
    private String codigo;

    @NotBlank(message="El campo DIRECCION es obligatorio")
    @Size(min=5, max=200)
    private String direccion;

    @NotNull(message="El campo CAPACIDAD ATENCION es obligatorio")
    @Positive(message="El campo CAPACIDAD ATENCION no puede ser negativo")
    @Min(1)
    private Integer capacidadAtencion;

    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA APERTURA es obligatorio")
    //No lleva notacion de tiempo porque hace referencia una sucursal ya abierta o próxima a abrir
    private LocalDate fechaApertura;

    @Positive(message="El campo REGION ID no puede ser negativo")
    private Long regionId;
}
