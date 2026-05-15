package com.dsy1103.mspedidos.mapper;

import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.modelo.DetallePedidoModelo;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {

    // Convierte de Entidad (BD) a DTO (Respuesta API)
    public DetallePedidoDTO toDTO(DetallePedidoModelo modelo) {
        if (modelo == null) return null;

        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setProductoId(modelo.getProductoId());
        dto.setCantidadPedido(modelo.getCantidadPedido());
        dto.setPrecioUnitario(modelo.getPrecioUnitario());
        dto.setSubtotal(modelo.getSubtotal());
        dto.setObservacion(modelo.getObservacion());
        dto.setFechaRegistro(modelo.getFechaRegistro());
        dto.setEstadoDetalle(modelo.isEstadoDetalle());

        // EXTRAEMOS EL ID DEL PADRE PEDIDO:
        if (modelo.getPedido() != null) {
            dto.setPedidoId(modelo.getPedido().getId());
        }

        return dto;
    }

    // Convierte de DTO a Entidad (Para Guardar)
    // Recibe el "padre" (pedido) para establecer la relación FK
    public DetallePedidoModelo toEntity(DetallePedidoDTO dto, PedidoModelo pedido) {
        if (dto == null) return null;

        DetallePedidoModelo modelo = new DetallePedidoModelo();
        modelo.setProductoId(dto.getProductoId());
        modelo.setCantidadPedido(dto.getCantidadPedido());
        modelo.setPrecioUnitario(dto.getPrecioUnitario());
        modelo.setSubtotal(dto.getSubtotal());
        modelo.setObservacion(dto.getObservacion());
        modelo.setFechaRegistro(dto.getFechaRegistro());
        modelo.setEstadoDetalle(dto.getEstadoDetalle());

        // Relación con el pedido padre
        modelo.setPedido(pedido);

        return modelo;
    }
}