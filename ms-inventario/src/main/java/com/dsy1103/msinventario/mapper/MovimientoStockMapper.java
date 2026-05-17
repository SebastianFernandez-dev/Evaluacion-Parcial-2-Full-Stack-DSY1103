package com.dsy1103.msinventario.mapper;

import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.model.MovimientoStockModel;

public class MovimientoStockMapper {

    public static MovimientoStockDTO toDTO(MovimientoStockModel mModel) {
        if (mModel == null) return null;

        return MovimientoStockDTO.builder()
                .id(mModel.getId())
                .tipo(mModel.getTipo())
                .cantidad(mModel.getCantidad())
                .motivo(mModel.getMotivo())
                .saldoPosterior(mModel.getSaldoPosterior())
                .fecha(mModel.getFecha())
                .aprobado(mModel.getAprobado())
                .inventarioId(mModel.getInventario().getId())
                .build();
    }

    public static MovimientoStockModel toEntity(MovimientoStockDTO mDTO) {
        if (mDTO == null) return null;

        return MovimientoStockModel.builder()
                .tipo(mDTO.getTipo())
                .cantidad(mDTO.getCantidad())
                .motivo(mDTO.getMotivo())
                .saldoPosterior(mDTO.getSaldoPosterior())
                .fecha(mDTO.getFecha())
                .aprobado(mDTO.getAprobado())
                .build();
        //el inventario se agrega en el service, para consultar a la bdd si existe en realidad
    }
}
