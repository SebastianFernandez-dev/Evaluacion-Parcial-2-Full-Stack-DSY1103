package com.dsy1103.msproveedores.dto;

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
public class ProveedorRequestDTO {

    @NotBlank(message="El campo NOMBRE es obligatorio")
    @Size(max=80)
    private String nombre;

    @NotBlank(message="El campo RAZON SOCIAL es obligatorio")
    @Size(min=5, max=120)
    private String razonSocial;

    @NotBlank(message="El campo DOCUMENTO fiscal es obligatorio")
    @Size(min=5, max=30)
    private String documentoFiscal;

    @NotBlank(message="El campo CORREO es obligatorio")
    @Email(message="Direccion de CORREO no valida")
    @Size(max=80)
    private String correoContacto;

    @NotBlank(message="El campo CIUDAD es obligatorio")
    @Size(min=5, max=80)
    private String ciudad;

    @NotNull(message="El campo CALIFICACION es obligatorio")
    @Positive(message="El campo CALIFICACION no puede ser negativo")
    @Min(1)
    @Max(5)
    private Integer calificacion;

    @NotNull(message="El campo ACTIVO es obligatorio")
    private Boolean activo;

    @NotNull(message="El campo FECHA REGISTRO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaRegistro;
}
