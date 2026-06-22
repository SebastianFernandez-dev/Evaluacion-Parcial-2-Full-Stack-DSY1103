package com.dsy1103.msinventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description="Modelo de transferencia de datos que registra transacciones individuales de alteración de stock (Entradas o Salidas)")
public class MovimientoStockDTO {

    @Schema(description="Identificador único del movimiento de stock (Autogenerado)", example="101", accessMode=Schema.AccessMode.READ_ONLY)
    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @Schema(description="Naturaleza del movimiento dentro del almacén", allowableValues={"ENTRADA", "SALIDA"}, example="ENTRADA")
    @NotBlank(message="El campo TIPO es obligatorio")
    @Size(min=4, max=20)
    private String tipo;

    @Schema(description="Unidades físicas involucradas en la transacción actual", minimum="1", example="50")
    @NotNull(message="El campo CANTIDAD es obligatorio")
    @Positive(message="El campo CANTIDAD no puede ser negativo")
    private Integer cantidad;

    @Schema(description="Justificación detallada de la causa del movimiento", minLength=5, maxLength=150, example="Reabastecimiento mensual de proveedor - Factura #984")
    @NotBlank(message="El campo TIPO MOTIVO es obligatorio")
    @Size(min=5, max=150)
    private String motivo;

    @Schema(description="Cálculo resultante del stock físico disponible inmediatamente después de aplicar este movimiento", minimum="0", example="500")
    @NotNull(message="El campo SALDO POSTERIOR es obligatorio")
    @Positive(message="El campo SALDO POSTERIOR no puede ser negativo")
    private Integer saldoPosterior;

    @Schema(description="Fecha en la que se ejecutó y asentó contablemente la transacción", example="2026-06-14")
    @NotNull(message="El campo FECHA es obligatorio")
    @PastOrPresent(message="Debe ingresar FECHA actual o pasada")
    private LocalDate fecha;

    @Schema(description="Estado de validación por parte del supervisor u operador logístico", example="true")
    @NotNull(message="El campo APROBADO es obligatorio")
    private Boolean aprobado;

    @Schema(description="ID del registro de inventario local que se ve afectado por este flujo de material", example="1")
    @NotNull(message="El campo INVENTARIO ID es obligatorio")
    @Positive(message="El campo INVENTARIO ID no puede ser negativo")
    private Long inventarioId;
}
