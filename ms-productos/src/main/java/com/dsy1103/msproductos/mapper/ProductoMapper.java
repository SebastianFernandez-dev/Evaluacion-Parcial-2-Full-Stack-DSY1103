package com.dsy1103.msproductos.mapper;

import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.model.ProductoModel;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(ProductoModel model) {
        if (model == null) return null;

        return ProductoDTO.builder()
                .id(model.getId())
                .nombreProducto(model.getNombreProducto())
                .descripcion(model.getDescripcion())
                .sku(model.getSku())
                .precio(model.getPrecio())
                .activoProducto(model.getActivoProducto())
                .fechaIngreso(model.getFechaIngreso())
                .categoriaId(model.getCategoria().getId())
                .build();
    }

    public ProductoModel toEntity(ProductoDTO dto, CategoriaModel categoria) {
        if (dto == null) return null;

        return ProductoModel.builder()
                .id(dto.getId())
                .nombreProducto(dto.getNombreProducto())
                .descripcion(dto.getDescripcion())
                .sku(dto.getSku())
                .precio(dto.getPrecio())
                .activoProducto(dto.getActivoProducto())
                .fechaIngreso(dto.getFechaIngreso())
                .categoria(categoria)
                .build();
    }
}
