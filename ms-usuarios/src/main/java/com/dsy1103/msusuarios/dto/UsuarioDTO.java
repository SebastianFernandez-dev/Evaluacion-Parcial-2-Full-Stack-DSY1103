package com.dsy1103.msusuarios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message="El campo Primer Nombre es obligatorio")
    @Size(min=6, max=20)
    private String primerNombre;

    @NotBlank(message="El campo Segundo Nombre es obligatorio")
    @Size(min=6, max=20)
    private String segundoNombre;

    @NotBlank(message="El campo Primer Apellido es obligatorio")
    @Size(min=6, max=20)
    private String primerApellido;

    @NotBlank(message="El campo Segundo Apellido es obligatorio")
    @Size(min=6, max=20)
    private String segundoApellido;

    @NotBlank(message="El campo CORREO es obligatorio")
    @Email(message="Direccion de CORREO no valida")
    @Size(max=80)
    private String correoUsuario;

    @NotNull(message = "El RUT es obligatorio")
    @Positive(message = "El RUT debe ser valido")
    private Integer rut;

    @NotNull(message = "El DV es obligatorio")
    @Positive(message = "El DV debe ser valido")
    @Size(min=0, max=1)
    private Integer dvRut;

    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA REGISTRO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRegistro;

}
