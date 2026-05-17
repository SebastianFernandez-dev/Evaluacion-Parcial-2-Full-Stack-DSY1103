package com.dsy1103.msinventario.service;


import com.dsy1103.msinventario.client.ProductoClient;
import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.dto.ProductoDTO;
import com.dsy1103.msinventario.mapper.InventarioMapper;
import com.dsy1103.msinventario.mapper.MovimientoStockMapper;
import com.dsy1103.msinventario.model.InventarioModel;
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
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private MovimientoStockRepository movimientoStockRepository;
    @Autowired
    private ProductoClient productoClient;

    public List<InventarioDTO> listarInventarios() {
        log.info("Listando todos los INVENTARIOS");

        return inventarioRepository.findAll()
                .stream()
                .map(InventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO obtenerInventarioPorId(Long id) {
        log.info("Obteniendo INVENTARIO por ID {}", id);
        InventarioDTO iDTO = inventarioRepository.findById(id)
                .map(InventarioMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Error: El INVENTARIO con ID "
                        + id + " no existe. No se pudo realizar la busqueda."));

        InventarioDTO iConPDTO = convertirConProducto(iDTO);

        iConPDTO.setListaMovimientosStock(movimientoStockRepository.findByInventarioId(id)
                .stream()
                .map(MovimientoStockMapper::toDTO)
                .collect(Collectors.toList()));

        return iConPDTO;
    }

    public List<InventarioDTO> listarInventariosConCantidadMayorActivos(Integer cantidadMayor) {
        log.info("Obteniendo INVENTARIOS con cantidad mayor a {} activos", cantidadMayor);
        return inventarioRepository.findByCantidadDisponibleGreaterThanAndActivoTrue(cantidadMayor)
                .stream()
                .map(InventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO guardarInventario(InventarioDTO iDTO) {
        log.info("Intentando registrar INVENTARIO con ID {}", iDTO.getId());
        InventarioModel iModel = InventarioMapper.toEntity(iDTO);

        InventarioModel guardado = inventarioRepository.save(iModel);
        log.info("INVENTARIO guardado exitosamente con ID: {}", guardado.getId());

        return InventarioMapper.toDTO(guardado);
    }

    public void actualizarInventario(InventarioDTO iDTO) {
        log.info("Actualizando INVENTARIO con ID {}", iDTO.getId());

        inventarioRepository.findById(iDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: INVENTARIO no encontrado."));

        inventarioRepository.save(InventarioModel.builder()
                .id(iDTO.getId())
                .codigo(iDTO.getCodigo())
                .ubicacion(iDTO.getUbicacion())
                .cantidadDisponible(iDTO.getCantidadDisponible())
                .stockMinimo(iDTO.getStockMinimo())
                .activo(iDTO.getActivo())
                .fechaRealizacion(iDTO.getFechaRealizacion())
                .productoId(iDTO.getProductoId())
                .build());
    }

    public void eliminarInventario(Long id) {
        log.warn("Eliminando INVENTARIO con ID: {}", id);

        if (!inventarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: INVENTARIO no encontrado.");
        }

        inventarioRepository.deleteById(id);
        log.info("INVENTARIO eliminado exitosamente con ID: {}", id);
    }

    private InventarioDTO convertirConProducto(InventarioDTO iEntrada){
        log.info("Intentando convertir con PRODUCTO ID: {}", iEntrada.getProductoId());

        InventarioDTO iSalida = InventarioDTO.builder()
                .id(iEntrada.getId())
                .codigo(iEntrada.getCodigo())
                .ubicacion(iEntrada.getUbicacion())
                .cantidadDisponible(iEntrada.getCantidadDisponible())
                .stockMinimo(iEntrada.getStockMinimo())
                .activo(iEntrada.getActivo())
                .fechaRealizacion(iEntrada.getFechaRealizacion())
                .productoId(iEntrada.getProductoId())
                .build();

        try{
            ProductoDTO pDTO = productoClient.obtenerProductoPorId(iEntrada.getProductoId());

            if (pDTO != null){
                iSalida.setNombreProducto(pDTO.getNombre());
                iSalida.setDescripcionProducto(pDTO.getDescripcion());
                iSalida.setSkuProducto(pDTO.getSku());
                iSalida.setPrecioProducto(pDTO.getPrecio());
                iSalida.setActivoProducto(pDTO.getActivo());
                iSalida.setFechaIngresoProducto(pDTO.getFechaIngreso());
                iSalida.setProductoCategoriaId(pDTO.getCategoriaId());

            }
        } catch (Exception e){
            iSalida.setNombreProducto("Servicio no disponible");
        }
        log.info("Conversion exitosa con ID: {}", iEntrada.getProductoId());

        return iSalida;
    }
}
