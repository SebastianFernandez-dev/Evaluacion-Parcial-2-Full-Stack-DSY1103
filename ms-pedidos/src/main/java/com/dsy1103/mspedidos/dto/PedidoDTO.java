package com.dsy1103.mspedidos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {


    @NotBlank(message = "El código de pedido es obligatorio")
    @Size(min = 6, max = 12, message = "El código debe tener entre 6 y 12 caracteres")
    private String codigoPedido;

    @NotNull(message = "La fecha no puede ser nula")
    @PastOrPresent(message = "La fecha debe ser actual o pasada")
    private LocalDateTime fechaPedido;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer totalPedido;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200)
    private String direccionEntrega;

    @NotNull(message = "El estado de pago es obligatorio")
    private Boolean pagadopedido;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    private List<DetallePedidoDTO> detalles;

    @NotNull(message = "Detalle Pedido es obligatorio")
    private String estadopedido;

}
