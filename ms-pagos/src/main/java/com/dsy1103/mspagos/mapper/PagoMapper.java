package com.dsy1103.mspagos.mapper;

import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.model.PagoModel;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(PagoModel model) {
        if (model == null) return null;

        return PagoDTO.builder()
                .id(model.getId())
                .codigoTransaccion(model.getCodigoTransaccion())
                .pedidoId(model.getPedidoId())
                .monto(model.getMonto())
                .metodoPago(model.getMetodoPago())
                .estadoPago(model.getEstadoPago())
                .fechaPago(model.getFechaPago())
                .activo(model.getActivo())
                .build();
    }

    public PagoModel toEntity(PagoDTO dto) {
        if (dto == null) return null;

        return PagoModel.builder()
                .id(dto.getId())
                .codigoTransaccion(dto.getCodigoTransaccion())
                .pedidoId(dto.getPedidoId())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .estadoPago(dto.getEstadoPago())
                .fechaPago(dto.getFechaPago())
                .activo(dto.getActivo())
                .build();
    }
}
