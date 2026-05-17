package com.dsy1103.msinventario.dto;

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
public class MovimientoStockDTO {

    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message="El campo TIPO MOVIMIENTO es obligatorio")
    @Size(min=4, max=20)
    private String tipoMovimiento;

    @NotNull(message="El campo CANTIDAD es obligatorio")
    @Positive(message="El campo CANTIDAD no puede ser negativo")
    private Integer cantidad;

    @NotBlank(message="El campo TIPO MOTIVO es obligatorio")
    @Size(min=5, max=150)
    private String motivo;

    @NotNull(message="El campo SALDO POSTERIOR es obligatorio")
    @Positive(message="El campo SALDO POSTERIOR no puede ser negativo")
    private Integer saldoPosterior;

    @NotNull(message="El campo FECHA MOVIMIENTO es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fechaMovimmiento;

    @NotNull(message="El campo APROBADO es obligatorio")
    private Boolean aprobado;

    @NotNull(message="El campo INVENTARIO ID es obligatorio")
    @Positive(message="El campo INVENTARIO ID no puede ser negativo")
    private Long inventarioId;
}
