package com.dsy1103.msenvios.service;

import com.dsy1103.msenvios.Client.PedidoClient;
import com.dsy1103.msenvios.Client.UsuarioClient;
import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.dto.PedidoDTO;
import com.dsy1103.msenvios.dto.UsuarioDTO;
import com.dsy1103.msenvios.exception.CodigoEnvioAlreadyExistsException;
import com.dsy1103.msenvios.mapper.EnvioMapper;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.repository.EnvioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private EnvioMapper envioMapper;
    @Autowired
    private PedidoClient pedidoClient;
    @Autowired
    private UsuarioClient usuarioClient;

    @Transactional(readOnly = true)
    public List<EnvioResponseDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Envios");
        return envioRepository.findAll().stream()
                .map(envioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnvioResponseDTO crear(EnvioRequestDTO dto) {
        try {
            log.info("Servicio: Intentando crear un nuevo envio para el pedido ID: {}", dto.getPedidoId());

            validarCodigoEnvioUnico(dto.getCodigoEnvio(), null);

            PedidoDTO pedidoRemoto = pedidoClient.obtenerPedidoPorId(dto.getPedidoId());
            if (pedidoRemoto == null) {
                throw new RuntimeException("Error: El pedido con ID " + dto.getPedidoId() + " no existe en el microservicio de Pedidos.");
            }

            UsuarioDTO usuarioRemoto = usuarioClient.obtenerUsuarioPorId(dto.getUsuarioId());
            if (usuarioRemoto == null) {
                throw new RuntimeException("El usuario con ID " + dto.getUsuarioId() + " no existe en el sistema.");
            }

            EnvioModelo modelo = envioMapper.toEntity(dto);
            EnvioModelo guardado = envioRepository.save(modelo);
            return envioMapper.toResponseDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear Envio: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public EnvioResponseDTO buscarPorId(Long id) {
        log.info("Buscando Envio con ID: {}", id);
        EnvioModelo envio = envioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Envio con ID {} no encontrado", id);
                    return new EntityNotFoundException("Envio no encontrado con ID: " + id);
                });
        return envioMapper.toResponseDTO(envio);
    }

    @Transactional
    public EnvioResponseDTO actualizar(Long id, EnvioRequestDTO dto) {
        log.info("Actualizando ENVIO con ID: {}", id);

        EnvioModelo existente = envioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: ENVIO no encontrado para actualizar."));

        validarCodigoEnvioUnico(dto.getCodigoEnvio(), id);

        existente.setCodigoEnvio(dto.getCodigoEnvio());
        existente.setPedidoId(dto.getPedidoId());
        existente.setUsuarioId(dto.getUsuarioId());
        existente.setDireccionDestino(dto.getDireccionDestino());
        existente.setEstadoEnvio(dto.getEstadoEnvio());
        existente.setFechaSalida(dto.getFechaSalida());
        existente.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        existente.setFechaEntregado(dto.getFechaEntregado());
        existente.setActivo(dto.getActivo());

        EnvioModelo actualizado = envioRepository.save(existente);
        log.info("ENVIO actualizado exitosamente con ID: {}", actualizado.getId());

        return envioMapper.toResponseDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar Envio ID: {}", id);
        if (!envioRepository.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Envio no encontrado");
        }
        envioRepository.deleteById(id);
        log.info("Envio ID: {} eliminado correctamente", id);
    }

    public List<EnvioModelo> obtenerEnviosEnRangoNoEntregados(LocalDateTime inicio, LocalDateTime fin) {
        return envioRepository.findEnviosEnRangoNoEntregados(inicio, fin);
    }

    private void validarCodigoEnvioUnico(String codigoEnvio, Long idExcluir) {
        envioRepository.findBycodigoEnvio(codigoEnvio).ifPresent(e -> {
            if (idExcluir == null || !e.getId().equals(idExcluir)) {
                throw new CodigoEnvioAlreadyExistsException(
                        "Error: El CODIGO DE ENVIO '" + codigoEnvio + "' ya esta registrado en otro envio");
            }
        });
    }
}
