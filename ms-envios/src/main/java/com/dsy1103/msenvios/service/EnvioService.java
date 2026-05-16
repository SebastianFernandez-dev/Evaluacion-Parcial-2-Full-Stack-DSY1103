package com.dsy1103.msenvios.service;


import com.dsy1103.msenvios.Client.PedidoClient;
import com.dsy1103.msenvios.Client.UsuarioClient;
import com.dsy1103.msenvios.dto.PedidoDTO;
import com.dsy1103.msenvios.dto.UsuarioDTO;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.dto.EnvioDTO;
import com.dsy1103.msenvios.mapper.EnvioMapper;
import com.dsy1103.msenvios.repository.EnvioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<EnvioDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Envios");
        return envioRepository.findAll().stream()
                .map(envio -> envioMapper.toDTO(envio)) // Uso de la instancia inyectada
                .collect(Collectors.toList());
    }

    @Transactional
    public EnvioDTO crear(EnvioDTO dto) {
        try {
            log.info("Servicio: Intentando crear un nuevo envío para el pedido ID: {}", dto.getPedidoId());
            // Levantamos el teléfono y llamamos al otro microservicio (al puerto 8084)
            // Le pasamos el 'pedidoId' que nos mandaron desde Postman
            PedidoDTO pedidoRemoto = pedidoClient.obtenerPedidoPorId(dto.getPedidoId());

            // Si la otra oficina nos dice que no encuentra nada (null), frenamos el proceso de inmediato
            if (pedidoRemoto == null) {
                throw new RuntimeException("Error: El pedido con ID " + dto.getPedidoId() + " no existe en el microservicio de Pedidos.");
            }

            // Llamada 2: Validar Usuario
            // Le pasamos el 'usuarioId' que viene en el JSON de Postman
            UsuarioDTO usuarioRemoto = usuarioClient.obtenerUsuarioPorId(dto.getUsuarioId());
            if (usuarioRemoto == null) {
                throw new RuntimeException("El usuario con ID " + dto.getUsuarioId() + " no existe en el sistema.");
            }

            EnvioModelo modelo = envioMapper.toEntity(dto);
            EnvioModelo guardado = envioRepository.save(modelo);
            return envioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear Envio: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public EnvioDTO buscarPorId(Long id) {
        log.info("Buscando Envio con ID: {}", id);
        EnvioModelo envio = envioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Envio con ID {} no encontrado", id);
                    return new EntityNotFoundException("Envio no encontrado con ID: " + id);
                });
        return envioMapper.toDTO(envio);
    }

    @Transactional
    public EnvioDTO actualizar(Long id, EnvioDTO dto) {
        EnvioModelo existente = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado con el ID: " + id));

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
        return envioMapper.toDTO(actualizado);
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

}
