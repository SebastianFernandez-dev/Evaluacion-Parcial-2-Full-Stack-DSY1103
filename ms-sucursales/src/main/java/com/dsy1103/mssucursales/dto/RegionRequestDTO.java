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
public class RegionRequestDTO {

    @NotBlank(message = "El campo NOMBRE es obligatorio")
    @Size(min = 2, max = 80)
    private String nombre;

    @NotBlank(message = "El campo CODIGO es obligatorio")
    @Size(min = 2, max = 20)
    private String codigo;

    @NotBlank(message = "El campo DESCRIPCION es obligatorio")
    @Size(min = 5, max = 200)
    private String descripcion;

    @NotBlank(message = "El campo PAIS es obligatorio")
    @Size(min = 2, max = 80)
    private String pais;

    @NotNull(message = "El campo FECHA CREACION es obligatorio")
    @PastOrPresent(message = "Debe ingresar FECHA actual o pasada")
    private LocalDate fechaCreacion;
}
