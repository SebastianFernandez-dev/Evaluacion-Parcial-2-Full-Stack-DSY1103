package com.dsy1103.msusuarios.mapper;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.modelo.PerfilModelo;
import org.springframework.stereotype.Component;



@Component
public class PerfilMapper {

    //Convierte de Entidad BD a DTO
    public PerfilDTO toDTO(PerfilModelo modelo) {
        if (modelo == null) return null;

        return PerfilDTO.builder()
                .id(modelo.getId())
                .nombrePerfil(modelo.getNombrePerfil())
                .descripcion(modelo.getDescripcion())
                .nivelAcessoPerfil(modelo.getNivelAcessoPerfil())
                .activo(modelo.getActivo())
                .fechaCreacionPerfil(modelo.getFechaCreacionPerfil())
                // Extraemos el ID del usuario
                .usuarioId(modelo.getUsuario() != null ? modelo.getUsuario().getId() : null)
                .build();
    }

    // Convierte de DTO a Entidad (Para Guardar)
    public PerfilModelo toEntity(PerfilDTO dto) {
        if (dto == null) return null;

        return PerfilModelo.builder()
                // El ID es autoincremental, así que no se setea
                .nombrePerfil(dto.getNombrePerfil())
                .descripcion(dto.getDescripcion())
                .nivelAcessoPerfil(dto.getNivelAcessoPerfil())
                .activo(dto.getActivo())
                .fechaCreacionPerfil(dto.getFechaCreacionPerfil())
                .build();
    }
}