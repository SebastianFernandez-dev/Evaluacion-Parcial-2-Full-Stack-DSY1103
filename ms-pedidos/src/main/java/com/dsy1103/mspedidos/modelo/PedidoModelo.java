package com.dsy1103.mspedidos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PEDIDO")

public class PedidoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_pedido",nullable = false,length = 12, unique = true)
    private String codigoPedido;

    @Column(name = "usuario_id",nullable = false)
    private Long usuarioId;

    @Column(name = "fecha_pedido",nullable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "total_pedido",nullable = false)
    private Integer totalPedido;

    @Column(name = "pagado_pedido",nullable = false)
    private Boolean pagadopedido;

    @Column(name = "direccion_entrega",nullable = false,length = 200)
    private String direccionEntrega;

    @Column(name = "estado_pedido",length = 30)
    private String estadoPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedidoModelo> detalles;

}
