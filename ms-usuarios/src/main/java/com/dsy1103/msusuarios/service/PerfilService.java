package com.dsy1103.msusuarios.service;


import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.mapper.PerfilMapper;
import com.dsy1103.msusuarios.modelo.PerfilModelo;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.PerfilRepository;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepo; // Necesario para la relación

    // 1. Listar todos los perfiles
    public List<PerfilDTO> listarTodo() {
        log.info("Consultando lista completa de perfiles");
        return perfilRepo.findAll().stream()
                .map(PerfilMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PerfilDTO crear(PerfilDTO dto) {
        try {
            log.info("Creando perfil '{}' para el usuario ID: {}", dto.getNombrePerfil(), dto.getUsuarioId());

            UsuarioModelo usuario = usuarioRepo.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Error: El usuario no existe"));

            // Convertimos DTO a Modelo y le asignamos el usuario encontrado
            PerfilModelo perfil = PerfilMapper.toEntity(dto);
            perfil.setUsuario(usuario);

            PerfilModelo guardado = perfilRepo.save(perfil);
            return PerfilMapper.toDTO(guardado);

        } catch (Exception e) {
            log.error("Error al crear el perfil: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public PerfilDTO actualizar(Long id, PerfilDTO dto) {
        log.info("Actualizando perfil ID: {}", id);

        PerfilModelo perfilExistente = perfilRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        // Actualización campo por campo manual
        perfilExistente.setNombrePerfil(dto.getNombrePerfil());
        perfilExistente.setDescripcion(dto.getDescripcion());
        perfilExistente.setNivelAcessoPerfil(dto.getNivelAcessoPerfil());
        perfilExistente.setActivo(dto.getActivo());
        perfilExistente.setFechaCreacionPerfil(dto.getFechaCreacionPerfil());

        return PerfilMapper.toDTO(perfilRepo.save(perfilExistente));
    }
}