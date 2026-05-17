package com.dsy1103.msreportes.dto;


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
public class ReporteDTO {

    @Positive(message="El campo ID no puede ser negativo")
    private Long id;

    @NotBlank(message="El campo DESCRIPCION es obligatorio")
    @Size(min=3, max=120)
    private String descripcion;

    @NotBlank(message="El campo TIPO es obligatorio")
    @Size(min=3, max=40)
    private String tipo;

    @NotNull(message="El campo TOTAL VENTAS es obligatorio")
    @Positive(message="El campo TOTAL VENTAS no puede ser negativo")
    @DecimalMin("0.0")
    private Double totalVentas;

    @NotNull(message="El campo CANTIDAD PEDIDOS es obligatorio")
    @Positive(message="El campo CANTIDAD PEDIDOS no puede ser negativo")
    private Integer cantidadPedidos;

    @NotNull(message="El campo CANTIDAD PAGOS es obligatorio")
    @Positive(message="El campo CANTIDAD PAGOS no puede ser negativo")
    private Integer cantidadPagos;

    //este dato lo registra automaticamente la base de datos
    private LocalDate fechaGeneracion;

    @NotNull(message="El campo PUBLICADO es obligatorio")
    private Boolean publicado;

    @Positive(message="El campo USUARIO ID no puede ser negativo")
    private Long usuarioId;
}
