package com.dsy1103.msusuarios.mapper;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.modelo.PerfilModelo;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    // De Modelo (Base de Datos) a DTO
    public PerfilDTO toDTO(PerfilModelo modelo) {
        if (modelo == null) return null;

        PerfilDTO dto = new PerfilDTO();
        dto.setId(modelo.getId());
        dto.setNombrePerfil(modelo.getNombrePerfil());
        dto.setDescripcion(modelo.getDescripcion());
        dto.setNivelAcessoPerfil(modelo.getNivelAcessoPerfil());
        dto.setActivo(modelo.getActivo());
        dto.setFechaCreacionPerfil(modelo.getFechaCreacionPerfil());

        // Extraemos el ID del usuario para el DTO
        if (modelo.getUsuario() != null) {
            dto.setUsuarioId(modelo.getUsuario().getId());
        }

        return dto;
    }

    // De DTO (Petición) a Modelo (Entidad JPA)
    public PerfilModelo toEntity(PerfilDTO dto) {
        if (dto == null) return null;

        PerfilModelo modelo = new PerfilModelo();
        // El ID no se setea aqui si es una creacion (es autoincremental)
        modelo.setNombrePerfil(dto.getNombrePerfil());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setNivelAcessoPerfil(dto.getNivelAcessoPerfil());
        modelo.setActivo(dto.getActivo());
        modelo.setFechaCreacionPerfil(dto.getFechaCreacionPerfil());

        return modelo;
    }
}