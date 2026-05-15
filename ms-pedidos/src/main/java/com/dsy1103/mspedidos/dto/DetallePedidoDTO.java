package com.dsy1103.mspedidos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DetallePedidoDTO {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Long pedidoId;

    @NotNull(message = "El ID de producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidadPedido;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    private Double precioUnitario;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", message = "El subtotal no puede ser negativo")
    private Double subtotal;

    @Size(max = 150, message = "La observación es muy larga")
    private String observacion;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDateTime fechaRegistro;

    @NotNull
    private Boolean estadoDetalle;

}
