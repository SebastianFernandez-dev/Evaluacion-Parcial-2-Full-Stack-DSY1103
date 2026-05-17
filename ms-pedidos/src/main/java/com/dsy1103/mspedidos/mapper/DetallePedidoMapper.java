package com.dsy1103.mspedidos.mapper;

import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.modelo.DetallePedidoModelo;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import lombok.Builder;
import org.springframework.stereotype.Component;


@Component // Spring sabe que es un componente inyectable
public class DetallePedidoMapper {

    public DetallePedidoDTO toDTO(DetallePedidoModelo modelo) {
        if (modelo == null) return null;

        return DetallePedidoDTO.builder()
                .productoId(modelo.getProductoId())
                .cantidadPedido(modelo.getCantidadPedido())
                .precioUnitario(modelo.getPrecioUnitario())
                .subtotal(modelo.getSubtotal())
                .observacion(modelo.getObservacion())
                .fechaRegistro(modelo.getFechaRegistro())
                .estadoDetalle(modelo.isEstadoDetalle())
                // Usamos un operador ternario cortito para extraer el ID del padre si no es nulo
                .pedidoId(modelo.getPedido() != null ? modelo.getPedido().getId() : null)
                .build();
    }

    public DetallePedidoModelo toEntity(DetallePedidoDTO dto, PedidoModelo pedido) {
        if (dto == null) return null;

        return DetallePedidoModelo.builder()
                .productoId(dto.getProductoId())
                .cantidadPedido(dto.getCantidadPedido())
                .precioUnitario(dto.getPrecioUnitario())
                .subtotal(dto.getSubtotal())
                .observacion(dto.getObservacion())
                .fechaRegistro(dto.getFechaRegistro())
                .estadoDetalle(dto.getEstadoDetalle())
                // Le pasamos directamente el pedido padre que viene por parámetro
                .pedido(pedido)
                .build();
    }
}
