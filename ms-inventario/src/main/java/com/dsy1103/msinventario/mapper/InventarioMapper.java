package com.dsy1103.msinventario.mapper;

import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.model.InventarioModel;

public class InventarioMapper {

    public static InventarioDTO toDTO(InventarioModel iModel) {
        if (iModel == null) return null;

        return InventarioDTO.builder()
                .id(iModel.getId())
                .codigo(iModel.getCodigo())
                .ubicacion(iModel.getUbicacion())
                .cantidadDisponible(iModel.getCantidadDisponible())
                .stockMinimo(iModel.getStockMinimo())
                .activo(iModel.getActivo())
                .fechaRealizacion(iModel.getFechaRealizacion())
                .sucursalId(iModel.getSucursalId())
                .proveedorId(iModel.getProveedorId())
                .productoId(iModel.getProductoId())
                .build();
    }

    public static InventarioModel toEntity(InventarioDTO iDTO) {
        if (iDTO == null) return null;

        return InventarioModel.builder()
                .codigo(iDTO.getCodigo())
                .ubicacion(iDTO.getUbicacion())
                .cantidadDisponible(iDTO.getCantidadDisponible())
                .stockMinimo(iDTO.getStockMinimo())
                .activo(iDTO.getActivo())
                .fechaRealizacion(iDTO.getFechaRealizacion())
                .sucursalId(iDTO.getSucursalId())
                .proveedorId(iDTO.getProveedorId())
                .productoId(iDTO.getProductoId())
                .build();
    }
}
