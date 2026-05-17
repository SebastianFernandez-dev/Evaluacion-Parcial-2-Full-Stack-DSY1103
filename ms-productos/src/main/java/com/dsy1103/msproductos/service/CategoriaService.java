package com.dsy1103.msproductos.service;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.mapper.CategoriaMapper;
import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> listarCategorias() {
        log.info("Listando todas las CATEGORIAS");

        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        log.info("Obteniendo CATEGORIA por ID {}", id);

        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: La CATEGORIA con ID "+id+" no pudo ser encontrada"));

    }

    public CategoriaDTO guardarCategoria(CategoriaDTO dto) {
        log.info("Registrando CATEGORIA: {}", dto.getNombreCategoria());
        CategoriaModel model = categoriaMapper.toEntity(dto);
        CategoriaModel guardado = categoriaRepository.save(model);
        log.info("CATEGORIA guardada exitosamente con ID: {}", guardado.getId());
        return categoriaMapper.toDTO(guardado);
    }

    public CategoriaDTO actualizarCategoria(CategoriaDTO dto) {
        log.info("Actualizando CATEGORIA con ID {}", dto.getId());

        CategoriaModel existente = categoriaRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Error: CATEGORIA no encontrada"));

        existente.setNombreCategoria(dto.getNombreCategoria());
        existente.setDescripcion(dto.getDescripcion());
        existente.setCodigoCategoria(dto.getCodigoCategoria());
        existente.setActivoCategoria(dto.getActivoCategoria());
        existente.setFechaCreacion(dto.getFechaCreacion());

        CategoriaModel actualizado = categoriaRepository.save(existente);
        return categoriaMapper.toDTO(actualizado);

    }

    public void eliminarCategoria(Long id) {
        log.warn("Eliminando CATEGORIA con ID: {}", id);
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: CATEGORIA no encontrada");
        }
        categoriaRepository.deleteById(id);
        log.info("CATEGORIA eliminada exitosamente con ID: {}", id);
    }

    public CategoriaModel guardarCategoriaModel(CategoriaModel model) {
        return categoriaRepository.save(model);
    }

}
