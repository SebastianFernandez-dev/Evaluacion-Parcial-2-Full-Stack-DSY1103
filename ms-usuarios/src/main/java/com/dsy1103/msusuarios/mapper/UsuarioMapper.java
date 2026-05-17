package com.dsy1103.msusuarios.mapper;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import org.springframework.stereotype.Component;

@Component // Esto hace que Spring lo reconozca
public class UsuarioMapper {

    //Convierte de Entidad BD a DTO
    public UsuarioDTO toDTO(UsuarioModelo usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .primerNombre(usuario.getPrimerNombre())
                .segundoNombre(usuario.getSegundoNombre())
                .primerApellido(usuario.getPrimerApellido())
                .segundoApellido(usuario.getSegundoApellido())
                .correoUsuario(usuario.getCorreoUsuario())
                .rut(usuario.getRut())
                .dvRut(usuario.getDvRut())
                .activo(usuario.getActivo())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }

    // onvierte de DTO a Entidad (Para Guardar)
    public UsuarioModelo toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        return UsuarioModelo.builder()
                // .id(dto.getId())
                .primerNombre(dto.getPrimerNombre())
                .segundoNombre(dto.getSegundoNombre())
                .primerApellido(dto.getPrimerApellido())
                .segundoApellido(dto.getSegundoApellido())
                .correoUsuario(dto.getCorreoUsuario())
                .rut(dto.getRut())
                .dvRut(dto.getDvRut())
                .activo(dto.getActivo())
                .fechaRegistro(dto.getFechaRegistro())
                .build();
    }
}
