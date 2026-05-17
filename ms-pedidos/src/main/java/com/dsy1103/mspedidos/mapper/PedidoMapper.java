package com.dsy1103.mspedidos.mapper;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.ArrayList;


@Component
public class PedidoMapper {

    @Autowired
    private DetallePedidoMapper detalleMapper; // Inyectamos el mapper pequeño

    // Convierte de Entidad (BD) a DTO (Respuesta API)
    public PedidoDTO toDTO(PedidoModelo modelo) {
        if (modelo == null) return null;

        return PedidoDTO.builder()
                .codigoPedido(modelo.getCodigoPedido())
                .fechaPedido(modelo.getFechaPedido())
                .totalPedido(modelo.getTotalPedido())
                .direccionEntrega(modelo.getDireccionEntrega())
                .pagadopedido(modelo.getPagadopedido())
                .usuarioId(modelo.getUsuarioId())

                .detalles(modelo.getDetalles() != null ?
                        modelo.getDetalles().stream()
                                .map(detalleMapper::toDTO)
                                .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    // Convierte de DTO a Entidad (Para Guardar)
    public PedidoModelo toEntity(PedidoDTO dto) {
        if (dto == null) return null;

        // 1. Primero construimos el objeto pedido
        PedidoModelo pedido = PedidoModelo.builder()
                .codigoPedido(dto.getCodigoPedido())
                .fechaPedido(dto.getFechaPedido())
                .totalPedido(dto.getTotalPedido())
                .direccionEntrega(dto.getDireccionEntrega())
                .pagadopedido(dto.getPagadopedido())
                .usuarioId(dto.getUsuarioId())
                .build();

        // 2. Ahora que 'pedido' ya existe, le asignamos de forma segura sus detalles relacionados
        if (dto.getDetalles() != null) {
            pedido.setDetalles(dto.getDetalles().stream()
                    .map(dDto -> detalleMapper.toEntity(dDto, pedido))
                    .collect(Collectors.toList()));
        }

        return pedido;
    }
}