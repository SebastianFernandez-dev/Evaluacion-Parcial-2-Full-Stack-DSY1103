package com.dsy1103.mspedidos.service;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.mapper.PedidoMapper;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import com.dsy1103.mspedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarTodos() {
        log.info("Iniciando consulta de todos los usuarios");
        return pedidoRepo.findAll().stream()
                .map(Pedido -> pedidoMapper.toDTO(Pedido))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        PedidoModelo usuario = pedidoRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario con ID {} no encontrado", id);
                    return new EntityNotFoundException("Usuario no encontrado con ID: " + id);
                });
        return pedidoMapper.toDTO(usuario);
    }

    @Transactional
    public PedidoDTO crear(PedidoDTO dto) {
        try {
            log.info("Creando nuevo Pedido: {}", dto.getCodigoPedido());
            PedidoModelo modelo = pedidoMapper.toEntity(dto);
            PedidoModelo guardado = pedidoRepo.save(modelo);
            return pedidoMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void actualizar(PedidoDTO pDTO) {
        log.info("Actualizando PEDIDO con ID: {}", pDTO.getId());

        PedidoModelo pedidoExistente = pedidoRepo.findById(pDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PEDIDO no encontrado para actualizar."));

        pedidoRepo.save(PedidoModelo.builder()
                .id(pDTO.getId()) // 🌟 Clave para hacer el UPDATE
                .codigoPedido(pedidoExistente.getCodigoPedido())
                .fechaPedido(pedidoExistente.getFechaPedido())
                .usuarioId(pedidoExistente.getUsuarioId())
                .totalPedido(pDTO.getTotalPedido())
                .pagadopedido(pDTO.getPagadopedido())
                .direccionEntrega(pDTO.getDireccionEntrega())
                .estadoPedido(pDTO.getEstadopedido())
                .detalles(pedidoExistente.getDetalles())
                .build());
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar usuario ID: {}", id);
        if (!pedidoRepo.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Usuario no encontrado");
        }
        pedidoRepo.deleteById(id);
        log.info("Usuario ID: {} eliminado correctamente", id);
    }


    // Medoto para pedidos Pagados y Ordenados
    @Transactional
    public List<PedidoModelo> obtenerPedidosPagadosYOrdenados() {
        return pedidoRepo.findPedidosPagadosOrdenadosPorFecha();
    }

}
