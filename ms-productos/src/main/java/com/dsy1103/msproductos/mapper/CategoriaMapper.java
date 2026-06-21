package com.dsy1103.msproductos.mapper;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.model.CategoriaModel;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
@Component
public class CategoriaMapper {

    private ProductoMapper productoMapper;

    public CategoriaDTO toDTO(CategoriaModel model) {
        if(model == null) return null;

        return CategoriaDTO.builder()
                .id(model.getId())
                .nombreCategoria(model.getNombreCategoria())
                .descripcion(model.getDescripcion())
                .codigoCategoria(model.getCodigoCategoria())
                .activoCategoria(model.getActivoCategoria())
                .fechaCreacion(model.getFechaCreacion())
                .listaProducto(model.getListaProducto() != null 
                ? model.getListaProducto().stream().map(productoMapper::toDTO)
                .collect(Collectors.toList()) : null)
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
                .fechaCreacion(dto.getFechaCreacion()).build();
    }
}
