package com.dsy1103.msenvios.service;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import com.dsy1103.msenvios.dto.SeguimientoDTO;
import com.dsy1103.msenvios.mapper.SeguimientoMapper;
import com.dsy1103.msenvios.repository.EnvioRepository;
import com.dsy1103.msenvios.repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepo;
    @Autowired
    private SeguimientoMapper seguimientoMapper;
    @Autowired
    private EnvioRepository envioRepository;


    @Transactional(readOnly = true)
    public List<SeguimientoDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Seguimientos");
        return seguimientoRepo.findAll().stream()
                .map(seguimiento -> seguimientoMapper.toDTO(seguimiento)) // Uso de la instancia inyectada
                .collect(Collectors.toList());
    }

    @Transactional
    public SeguimientoDTO crear(SeguimientoDTO dto) {
        try {
            EnvioModelo envioExistente = envioRepository.findById(dto.getEnvioId())
                    .orElseThrow(() -> new RuntimeException("El envío con ID " + dto.getEnvioId() + " no existe."));

            SeguimientoModelo modelo = seguimientoMapper.toEntity(dto);

            modelo.setEnvio(envioExistente);

            SeguimientoModelo guardado = seguimientoRepo.save(modelo);
            return seguimientoMapper.toDTO(guardado);

        } catch (Exception e) {
            log.error("Error al crear Seguimiento: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public SeguimientoDTO buscarPorId(Long id) {
        log.info("Buscando Seguimiento con ID: {}", id);
        SeguimientoModelo envio = seguimientoRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Seguimiento con ID {} no encontrado", id);
                    return new EntityNotFoundException("Seguimiento no encontrado con ID: " + id);
                });
        return seguimientoMapper.toDTO(envio);
    }

    @Transactional
    public SeguimientoDTO actualizar(Long id, SeguimientoDTO dto) {
        SeguimientoModelo existente = seguimientoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado con el ID: " + id));
        // Si el JSON viene con un envioId diferente, actualizamos la relación
        EnvioModelo envio = envioRepository.findById(dto.getEnvioId())
                .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado con ID: " + dto.getEnvioId()));

        existente.setEnvio(envio);// Actualiza la relación por si cambió de paquete
        existente.setEstadoSegui(dto.getEstadoSegui());
        existente.setUbiAtual(dto.getUbiAtual());
        existente.setObservacion(dto.getObservacion());
        existente.setFechaSegui(dto.getFechaSegui());
        existente.setVisible(dto.getVisible());

        SeguimientoModelo actualizado = seguimientoRepo.save(existente);
        return seguimientoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar Seguimiento ID: {}", id);
        if (!seguimientoRepo.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Seguimiento no encontrado");
        }
        seguimientoRepo.deleteById(id);
        log.info("Seguimiento ID: {} eliminado correctamente", id);
    }

}
