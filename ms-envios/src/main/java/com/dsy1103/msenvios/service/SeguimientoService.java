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
    public void actualizarSeguimiento(SeguimientoDTO sDTO) {

        log.info("Actualizando SEGUIMIENTO con ID: {}", sDTO.getId());
        seguimientoRepo.findById(sDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: SEGUIMIENTO no encontrado para actualizar."));

        EnvioModelo envio = envioRepository.findById(sDTO.getEnvioId())
                .orElseThrow(() -> new EntityNotFoundException("Error: ENVIO no encontrado con ID: " + sDTO.getEnvioId()));

        seguimientoRepo.save(SeguimientoModelo.builder()
                .id(sDTO.getId()) // Esencial para que Hibernate actualice el registro existente
                .envio(envio)    // Pasamos la entidad completa del envío que encontramos recién
                .estadoSegui(sDTO.getEstadoSegui())
                .ubiAtual(sDTO.getUbiAtual())
                .observacion(sDTO.getObservacion())
                .fechaSegui(sDTO.getFechaSegui())
                .visible(sDTO.getVisible())
                .build());
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
