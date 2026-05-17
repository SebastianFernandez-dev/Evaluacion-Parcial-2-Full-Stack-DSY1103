package com.dsy1103.msusuarios.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {

    private Long id;

    @NotBlank(message="El campo Primer NOMBRE es obligatorio")
    @Size(min=6, max=20)
    private String nombrePerfil;

    @NotBlank(message="El campo DESCRIPCION es obligatorio")
    @Size(min=5, max=200)
    private String descripcion;

    @NotNull(message="El campo NIVEL ACCESO es obligatorio")
    @Min(value = 1, message = "El NIVEL de acceso minimo es 1")
    private Integer nivelAcessoPerfil;

    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA es obligatorio")
    @PastOrPresent(message="Debe ingresar una FECHA Valida")
    private LocalDate fechaCreacionPerfil;


    private Long usuarioId;

}
