package com.dsy1103.msusuarios.mapper;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import org.springframework.stereotype.Component;

@Component // Esto hace que Spring lo reconozca
public class UsuarioMapper {

    public UsuarioDTO toDTO(UsuarioModelo usuario){
        if (usuario == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setPrimerNombre(usuario.getPrimerNombre());
        dto.setSegundoNombre(usuario.getSegundoNombre());
        dto.setPrimerApellido(usuario.getPrimerApellido());
        dto.setSegundoApellido(usuario.getSegundoApellido());
        dto.setCorreoUsuario(usuario.getCorreoUsuario());
        dto.setRut(usuario.getRut());
        dto.setDvRut(usuario.getDvRut());
        dto.setActivo(usuario.getActivo());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }

    public UsuarioModelo toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        UsuarioModelo modelo = new UsuarioModelo();
        modelo.setPrimerNombre(dto.getPrimerNombre());
        modelo.setSegundoNombre(dto.getSegundoNombre());
        modelo.setPrimerApellido(dto.getPrimerApellido());
        modelo.setSegundoApellido(dto.getSegundoApellido());
        modelo.setCorreoUsuario(dto.getCorreoUsuario());
        modelo.setRut(dto.getRut());
        modelo.setDvRut(dto.getDvRut());
        modelo.setActivo(dto.getActivo());
        modelo.setFechaRegistro(dto.getFechaRegistro());
        return modelo;
    }
}