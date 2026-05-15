package com.dsy1103.msusuarios.service;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.mapper.UsuarioMapper;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private UsuarioMapper usuarioMapper;


    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        log.info("Iniciando consulta de todos los usuarios");
        return usuarioRepo.findAll().stream()
                .map(usuario -> usuarioMapper.toDTO(usuario)) // Uso de la instancia inyectada
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        UsuarioModelo usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario con ID {} no encontrado", id);
                    return new EntityNotFoundException("Usuario no encontrado con ID: " + id);
                });
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO crear(UsuarioDTO dto) {
        try {
            log.info("Creando nuevo usuario con correo: {}", dto.getCorreoUsuario());
            UsuarioModelo modelo = usuarioMapper.toEntity(dto);
            UsuarioModelo guardado = usuarioRepo.save(modelo);
            return usuarioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        try {
            log.info("Iniciando actualización de usuario con ID: {}", id);

            UsuarioModelo usuarioExistente = usuarioRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se puede actualizar: ID " + id + " no encontrado"));

            // Actualizamos los campos manualmente para mantener la misma instancia
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

            return usuarioMapper.toDTO(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar usuario ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar usuario ID: {}", id);
        if (!usuarioRepo.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Usuario no encontrado");
        }
        usuarioRepo.deleteById(id);
        log.info("Usuario ID: {} eliminado correctamente", id);
    }
}