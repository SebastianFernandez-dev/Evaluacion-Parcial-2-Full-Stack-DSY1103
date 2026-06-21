package com.dsy1103.msusuarios.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PedidoDTO {
    private Long id;
    private String codigoPedido;
    private LocalDateTime fechaPedido;
    private Integer totalPedido;
    private String direccionEntrega;
    private Boolean pagadopedido;
    private String estadopedido;
}