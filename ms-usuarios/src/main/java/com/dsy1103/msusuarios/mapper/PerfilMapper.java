package com.dsy1103.msusuarios.mapper;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.modelo.PerfilModelo;

public class PerfilMapper {

    public static PerfilDTO toDTO(PerfilModelo perfil){
        if (perfil == null) return null;
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setNombrePerfil(perfil.getNombrePerfil());
        dto.setDescripcion(perfil.getDescripcion());
        dto.setNivelAcessoPerfil(perfil.getNivelAcessoPerfil());
        dto.setActivo(perfil.getActivo());
        dto.setFechaCreacionPerfil(perfil.getFechaCreacionPerfil());

        if (perfil.getUsuario() != null){
            dto.setUsuarioId(perfil.getUsuario().getId());
        }

        return dto;
    }

    public static PerfilModelo toEntity(PerfilDTO dto) {
        if (dto == null) return null;

        PerfilModelo modelo = new PerfilModelo();
        modelo.setId(dto.getId());
        modelo.setNombrePerfil(dto.getNombrePerfil());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setNivelAcessoPerfil(dto.getNivelAcessoPerfil());
        modelo.setActivo(dto.getActivo());
        modelo.setFechaCreacionPerfil(dto.getFechaCreacionPerfil());

        return modelo;
    }



}
