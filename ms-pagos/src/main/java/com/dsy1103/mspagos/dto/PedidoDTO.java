package com.dsy1103.mspagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private String codigoPedido;
    private Long usuarioId;
    private LocalDateTime fechaPedido;
    private Integer totalPedido;
    private Boolean pagadopedido;
    private String direccionEntrega;
    private String estadoPedido;
}
