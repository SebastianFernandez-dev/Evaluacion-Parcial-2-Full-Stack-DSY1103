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
public class ContratoRequestDTO {

    @NotBlank(message="El campo NUMERO es obligatorio")
    @Size(min=4, max=40)
    private String numero;

    @NotBlank(message="El campo TIPO es obligatorio")
    @Size(min=3, max=30)
    private String tipo;

    @NotNull(message="El campo VALOR es obligatorio")
    @Positive(message="El campo VALOR no puede ser negativo")
    @DecimalMin("0.0")
    @DecimalMax("99.0")
    private Double valor;

    @NotNull(message="El campo FECHA INICIO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaInicio;

    @NotNull(message="El campo FECHA FIN es obligatorio")
    @Future(message="Debe ingresar una FECHA futura")
    private LocalDate fechaFin;

    @NotNull(message="El campo VIGENTE es obligatorio")
    private Boolean vigente;

    @NotBlank(message="El campo OBSERVACIONES es obligatorio")
    @Size(min=5, max=200)
    private String observaciones;

    @NotNull(message="El campo PROVEEDOR ID es obligatorio")
    @Positive(message="El campo PROVEEDOR ID no puede ser negativo")
    private Long proveedorId;
}
