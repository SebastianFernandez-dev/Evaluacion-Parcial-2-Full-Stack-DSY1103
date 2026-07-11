package com.dsy1103.msreportes.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteRequestDTO {

    @NotBlank(message = "El campo DESCRIPCION es obligatorio")
    @Size(min = 3, max = 120)
    private String descripcion;

    @NotBlank(message = "El campo TIPO es obligatorio")
    @Size(min = 3, max = 40)
    private String tipo;

    @NotNull(message = "El campo TOTAL VENTAS es obligatorio")
    @Positive(message = "El campo TOTAL VENTAS no puede ser negativo")
    @DecimalMin("0.0")
    private Double totalVentas;

    @NotNull(message = "El campo CANTIDAD PEDIDOS es obligatorio")
    @Positive(message = "El campo CANTIDAD PEDIDOS no puede ser negativo")
    private Integer cantidadPedidos;

    @NotNull(message = "El campo CANTIDAD PAGOS es obligatorio")
    @Positive(message = "El campo CANTIDAD PAGOS no puede ser negativo")
    private Integer cantidadPagos;

    @NotNull(message = "El campo PUBLICADO es obligatorio")
    private Boolean publicado;

    @NotNull(message = "El campo USUARIO ID es obligatorio")
    @Positive(message = "El campo USUARIO ID no puede ser negativo")
    private Long usuarioId;
}
