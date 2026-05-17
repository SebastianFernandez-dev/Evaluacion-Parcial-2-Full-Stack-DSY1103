package com.dsy1103.msinventario.service;

import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.mapper.MovimientoStockMapper;
import com.dsy1103.msinventario.model.InventarioModel;
import com.dsy1103.msinventario.model.MovimientoStockModel;
import com.dsy1103.msinventario.repository.InventarioRepository;
import com.dsy1103.msinventario.repository.MovimientoStockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovimientoStockService {

    @Autowired
    private MovimientoStockRepository movimientoStockRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<MovimientoStockDTO> listarMovimientos() {
        log.info("Listando todos los MOVIMIENTOS");

        return movimientoStockRepository.findAll()
                .stream()
                .map(MovimientoStockMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MovimientoStockDTO obtenerMovimientoPorId(Long id) {
        log.info("Obteniendo MOVIMIENTO por ID {}", id);

        return movimientoStockRepository.findById(id)
                .map(MovimientoStockMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El MOVIMIENTO con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));
    }

    public List<MovimientoStockDTO> listarMovimientosPorInventario(Long iId) {
        log.info("Listando MOVIMIENTOS por ID del INVENTARIO {}", iId);

        return movimientoStockRepository.findByInventarioId(iId)
                .stream()
                .map(MovimientoStockMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MovimientoStockDTO guardarMovimiento(MovimientoStockDTO iDTO) {
        log.info("Intentando registrar MOVIMIENTO ID: {}", iDTO.getId());
        MovimientoStockModel movimiento = MovimientoStockMapper.toEntity(iDTO);

        InventarioModel inventario = inventarioRepository.findById(iDTO.getInventarioId())
                .orElseThrow(() -> new EntityNotFoundException("Error: INVENTARIO con ID "
                        + iDTO.getInventarioId() + " no existe. No se puede registrar el MOVIMIENTO."));

        movimiento.setInventario(inventario);
        MovimientoStockModel guardado = movimientoStockRepository.save(movimiento);
        log.info("MOVIMIENTO guardado exitosamente con ID: {}", guardado.getId());

        return MovimientoStockMapper.toDTO(guardado);
    }

    public void actualizarMovimiento(MovimientoStockDTO mDTO) {
        log.info("Actualizando MOVIMIENTO con ID: {}", mDTO.getId());

        MovimientoStockModel mExistente = movimientoStockRepository.findById(mDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: MOVIMIENTO no encontrado."));

        if (mDTO.getInventarioId() != null) {
            InventarioModel p = inventarioRepository.findById(mDTO.getInventarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Error: INVENTARIO no encontrado."));
            mExistente.setInventario(p);
        }

        movimientoStockRepository.save(MovimientoStockModel.builder()
                .id(mDTO.getId())
                .tipo(mDTO.getTipo())
                .cantidad(mDTO.getCantidad())
                .motivo(mDTO.getMotivo())
                .saldoPosterior(mDTO.getSaldoPosterior())
                .fecha(mDTO.getFecha())
                .aprobado(mDTO.getAprobado())
                .inventario(mExistente.getInventario())
                .build());
    }

    public void eliminarMovimiento(Long id) {
        log.warn("Eliminando MOVIMIENTO con ID: {}", id);

        if (!movimientoStockRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: MOVIMIENTO no encontrado.");
        }

        movimientoStockRepository.deleteById(id);
        log.info("MOVIMIENTO eliminado exitosamente con ID: {}", id);
    }
}
