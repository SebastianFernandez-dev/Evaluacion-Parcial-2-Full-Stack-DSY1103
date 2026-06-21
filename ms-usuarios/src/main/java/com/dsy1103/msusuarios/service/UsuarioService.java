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
    // En tu UsuarioService / UsuarioServiceImpl
    public void actualizar(UsuarioDTO uDTO) {
        // 🪵 Log de control con el ID
        log.info("Actualizando USUARIO con ID: {}", uDTO.getId());

        // Verificamos si el usuario existe en la base de datos
        UsuarioModelo uExistente = usuarioRepo.findById(uDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: USUARIO no encontrado."));

        // Guardamos reconstruyendo el objeto con el Builder (Estilo de tu equipo)
        usuarioRepo.save(UsuarioModelo.builder()
                .id(uDTO.getId()) // ¡Obligatorio para que actualice y no cree uno nuevo!
                .primerNombre(uDTO.getPrimerNombre())
                .segundoNombre(uDTO.getSegundoNombre())
                .primerApellido(uDTO.getPrimerApellido())
                .segundoApellido(uDTO.getSegundoApellido())
                .correoUsuario(uDTO.getCorreoUsuario())
                .rut(uDTO.getRut())
                .dvRut(uDTO.getDvRut())
                .activo(uDTO.getActivo())
                .fechaRegistro(uDTO.getFechaRegistro())
                .build());
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