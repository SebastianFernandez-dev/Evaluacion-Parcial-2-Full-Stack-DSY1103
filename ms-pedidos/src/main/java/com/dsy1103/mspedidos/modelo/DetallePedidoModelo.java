package com.dsy1103.mspedidos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DETALLEPEDIDO")

public class DetallePedidoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "producto_id",nullable = false)
    private Long productoId;

    @Column(name = "cantidad_pedido",nullable = false)
    private Integer cantidadPedido;

    @Column(name = "precio_unitario",nullable = false)
    private Double precioUnitario;

    @Column(name = "subtotal",nullable = false)
    private Double subtotal;

    @Column(name = "observacion",nullable = false, length = 150)
    private String observacion;

    @Column(name = "fecha_registro",nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "estado_detalle", nullable = false)
    private boolean estadoDetalle = true;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoModelo pedido;

}
