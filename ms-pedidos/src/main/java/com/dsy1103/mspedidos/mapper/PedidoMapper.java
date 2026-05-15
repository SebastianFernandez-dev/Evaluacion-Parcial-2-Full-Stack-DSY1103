package com.dsy1103.mspedidos.mapper;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.ArrayList;

@Component
public class PedidoMapper {

    @Autowired
    private DetallePedidoMapper detalleMapper; // Inyectamos el mapper pequeño

    public PedidoDTO toDTO(PedidoModelo modelo) {
        if (modelo == null) return null;

        PedidoDTO dto = new PedidoDTO();
        dto.setCodigoPedido(modelo.getCodigoPedido());
        dto.setFechaPedido(modelo.getFechaPedido());
        dto.setTotalPedido(modelo.getTotalPedido());
        dto.setDireccionEntrega(modelo.getDireccionEntrega());
        dto.setPagadopedido(modelo.getPagadopedido());
        dto.setUsuarioId(modelo.getUsuarioId());

        // Delegamos la conversión de la lista al detalleMapper
        if (modelo.getDetalles() != null) {
            dto.setDetalles(modelo.getDetalles().stream()
                    .map(detalleMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setDetalles(new ArrayList<>());
        }

        return dto;
    }

    public PedidoModelo toEntity(PedidoDTO dto) {
        if (dto == null) return null;

        PedidoModelo pedido = new PedidoModelo();
        pedido.setCodigoPedido(dto.getCodigoPedido());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setTotalPedido(dto.getTotalPedido());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setPagadopedido(dto.getPagadopedido());
        pedido.setUsuarioId(dto.getUsuarioId());

        if (dto.getDetalles() != null) {
            pedido.setDetalles(dto.getDetalles().stream()
                    .map(dDto -> detalleMapper.toEntity(dDto, pedido))
                    .collect(Collectors.toList()));
        }

        return pedido;
    }
}