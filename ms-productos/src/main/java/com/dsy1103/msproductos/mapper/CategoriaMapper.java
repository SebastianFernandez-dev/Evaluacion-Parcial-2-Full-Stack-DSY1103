package com.dsy1103.msproductos.mapper;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.model.CategoriaModel;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(CategoriaModel model) {
        if(model == null) return null;

        return CategoriaDTO.builder()
                .id(model.getId())
                .nombreCategoria(model.getNombreCategoria())
                .descripcion(model.getDescripcion())
                .codigoCategoria(model.getCodigoCategoria())
                .activoCategoria(model.getActivoCategoria())
                .fechaCreacion(model.getFechaCreacion())
                .listaProducto(model.getListaProducto())
                .build();
    }

    public CategoriaModel toEntity(CategoriaDTO dto) {
        if(dto == null) return null;

        return CategoriaModel.builder()
                .id(dto.getId())
                .nombreCategoria(dto.getNombreCategoria())
                .descripcion(dto.getDescripcion())
                .codigoCategoria(dto.getCodigoCategoria())
                .activoCategoria(dto.getActivoCategoria())
                .fechaCreacion(dto.getFechaCreacion())
                .listaProducto(dto.getListaProducto())
                .build();
    }
}
