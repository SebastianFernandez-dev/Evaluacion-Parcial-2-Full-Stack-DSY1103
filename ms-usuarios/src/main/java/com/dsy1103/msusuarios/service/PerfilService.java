package com.dsy1103.msusuarios.service;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.mapper.PerfilMapper;
import com.dsy1103.msusuarios.modelo.PerfilModelo;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.PerfilRepository;
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
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PerfilMapper perfilMapper; // Inyección del mapper como componente

    @Transactional(readOnly = true)
    public List<PerfilDTO> listarTodo() {
        log.info("Consultando lista completa de perfiles");
        return perfilRepo.findAll().stream()
                .map(perfil -> perfilMapper.toDTO(perfil))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PerfilDTO buscarPorId(Long id) {
        log.info("Buscando perfil con ID: {}", id);
        PerfilModelo perfil = perfilRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Perfil ID {} no encontrado", id);
                    return new EntityNotFoundException("Perfil no encontrado");
                });
        return perfilMapper.toDTO(perfil);
    }

    @Transactional
    public PerfilDTO crear(PerfilDTO dto) {
        try {
            log.info("Creando perfil '{}' para usuario ID: {}", dto.getNombrePerfil(), dto.getUsuarioId());

            // validamos que el usuario perfil existe
            UsuarioModelo usuario = usuarioRepo.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("No se puede crear perfil: Usuario no existe"));

            PerfilModelo perfil = perfilMapper.toEntity(dto);
            perfil.setUsuario(usuario);

            PerfilModelo guardado = perfilRepo.save(perfil);
            log.info("Perfil creado con éxito. ID: {}", guardado.getId());
            return perfilMapper.toDTO(guardado);

        } catch (Exception e) {
            log.error("Error al crear perfil: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void actualizar(PerfilDTO pDTO) {
        log.info("Actualizando PERFIL con ID: {}", pDTO.getId());

        PerfilModelo perfilExistente = perfilRepo.findById(pDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PERFIL no encontrado para actualizar"));

        perfilRepo.save(PerfilModelo.builder()
                .id(pDTO.getId()) // Para que Hibernate haga UPDATE y no INSERT!
                .nombrePerfil(pDTO.getNombrePerfil())
                .descripcion(pDTO.getDescripcion())
                .nivelAcessoPerfil(pDTO.getNivelAcessoPerfil())
                .activo(pDTO.getActivo())
                .fechaCreacionPerfil(pDTO.getFechaCreacionPerfil())
                .usuario(perfilExistente.getUsuario())
                .build());
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar perfil ID: {}", id);
        if (!perfilRepo.existsById(id)) {
            log.error("No se pudo eliminar, el perfil ID {} no existe", id);
            throw new EntityNotFoundException("El perfil no existe");
        }
        perfilRepo.deleteById(id);
        log.info("Perfil ID {} eliminado con éxito", id);
    }
}