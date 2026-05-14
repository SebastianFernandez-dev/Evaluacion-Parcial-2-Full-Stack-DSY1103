package com.dsy1103.msusuarios.service;

import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.mapper.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<UsuarioDTO> listarTodos() {
        log.info("Iniciando consulta de todos los usuarios");
        return usuarioRepo.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        UsuarioModelo usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO crear(UsuarioDTO dto) {
        try {
            log.info("Creando nuevo usuario con correo: {}", dto.getCorreoUsuario());
            UsuarioModelo modelo = UsuarioMapper.toEntity(dto);
            UsuarioModelo guardado = usuarioRepo.save(modelo);
            return UsuarioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage());
            throw e;
        }
    }


    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        try {
            log.info("Iniciando actualización de usuario con ID: {}", id);

            UsuarioModelo usuarioExistente = usuarioRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se puede actualizar: Usuario con ID " + id + " no encontrado"));

            usuarioExistente.setPrimerNombre(dto.getPrimerNombre());
            usuarioExistente.setSegundoNombre(dto.getSegundoNombre());
            usuarioExistente.setPrimerApellido(dto.getPrimerApellido());
            usuarioExistente.setSegundoApellido(dto.getSegundoApellido());
            usuarioExistente.setCorreoUsuario(dto.getCorreoUsuario());
            usuarioExistente.setRut(dto.getRut());
            usuarioExistente.setDvRut(dto.getDvRut());
            usuarioExistente.setActivo(dto.getActivo());
            usuarioExistente.setFechaRegistro(dto.getFechaRegistro());

            UsuarioModelo actualizado = usuarioRepo.save(usuarioExistente);
            log.info("Usuario con ID: {} actualizado exitosamente", id);

            return UsuarioMapper.toDTO(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar usuario con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        if (!usuarioRepo.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Usuario no encontrado");
        }
        usuarioRepo.deleteById(id);
    }

}
