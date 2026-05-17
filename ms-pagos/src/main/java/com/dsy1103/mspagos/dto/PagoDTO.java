package com.dsy1103.mspagos.dto;

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
public class PagoDTO {

    @Positive(message = "El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message = "El campo CODIGO_TRANSACCION es obligatorio")
    @Size(min = 4,max = 40)
    private String codigoTransaccion;

    @NotNull(message = "El campo PEDIDO_ID es obligatorio")
    @Positive(message = "El campo PEDIDO_ID no puede ser negativo")
    private Long pedidoId;

    @NotNull(message = "El campo MONTO es obligatorio")
    @DecimalMin("0.0")
    private Double monto;

    @NotBlank(message = "El campo METODO_PAGO es obligatorio")
    @Size(min = 3,max = 30)
    private String metodoPago;

    @NotBlank(message = "El campo ESTADO_PAGO es obligatorio")
    @Size(min = 3,max = 20)
    private String estadoPago;

    @NotNull(message = "El campo FECHA_PAGO es obligatorio")
    @PastOrPresent(message = "Debe ingresar FECHA actual o pasada")
    private LocalDate fechaPago;

    @NotNull(message = "el campo ACTIVO_PAGO es obligatorio")
    private Boolean activo;
}
