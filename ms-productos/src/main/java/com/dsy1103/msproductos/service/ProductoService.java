package com.dsy1103.msproductos.service;

import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.mapper.ProductoMapper;
import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.model.ProductoModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
import com.dsy1103.msproductos.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> listarProductos() {
        log.info("Listando todos los PRODUCTOS");

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        log.info("Obteniendo PRODUCTO po ID {}", id);

        return productoRepository.findById(id)
                .map(productoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                "Error: El PRODUCTO con ID "+id+" no pudo ser encontrado"));
    }

    public ProductoDTO guardarProducto(ProductoDTO dto) {
        log.info("Registrando PRODUCTO: {}", dto.getId());

        CategoriaModel categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException(
                "Error: La CATEGORIA con ID "+dto.getCategoriaId()+" no existe"));
        ProductoModel model = productoMapper.toEntity(dto, categoria);
        ProductoModel guardado = productoRepository.save(model);
        log.info("PRODUCTO guardado exitosamente con ID: {}", guardado.getId());
        return productoMapper.toDTO(guardado);
    }

    public ProductoDTO actualizarProducto(ProductoDTO dto) {
        log.info("Actualizando PRODUCTO con ID {}", dto.getId());

        ProductoModel existente = productoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: PRODUCTO no encontrado"));

        CategoriaModel categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: La CATEGORIA con ID " + dto.getCategoriaId() + " no existe"));

        existente.setNombreProducto(dto.getNombreProducto());
        existente.setDescripcion(dto.getDescripcion());
        existente.setSku(dto.getSku());
        existente.setPrecio(dto.getPrecio());
        existente.setActivoProducto(dto.getActivoProducto());
        existente.setFechaIngreso(dto.getFechaIngreso());
        existente.setCategoria(categoria);

        ProductoModel actualizado = productoRepository.save(existente);
        return productoMapper.toDTO(actualizado);
    }

    public void eliminarProducto(Long id) {
        log.warn("Eliminando PRODUCTO con ID: {}", id);
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: PRODUCTO no encontrado");
        }
        productoRepository.deleteById(id);
        log.info("PRODUCTO eliminado exitosamente con ID: {}", id);
    }

}
