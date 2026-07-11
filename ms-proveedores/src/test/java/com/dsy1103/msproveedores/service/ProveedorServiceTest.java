package com.dsy1103.msproveedores.service;

import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.exception.DocumentoFiscalAlreadyExistsException;
import com.dsy1103.msproveedores.mapper.ContratoMapper;
import com.dsy1103.msproveedores.mapper.ProveedorMapper;
import com.dsy1103.msproveedores.model.ProveedorModel;
import com.dsy1103.msproveedores.repository.ContratoRepository;
import com.dsy1103.msproveedores.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;
    @Mock
    private ContratoRepository contratoRepository;
    @Mock
    private ProveedorMapper proveedorMapper;
    @Mock
    private ContratoMapper contratoMapper;
    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    @DisplayName("Debe listar todos los proveedores exitosamente")
    void debeListarProveedoresExitosamente() {
        ProveedorModel proveedor = ProveedorModel.builder()
                .id(1L).nombre("Proveedor Test").build();
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L).nombre("Proveedor Test").build();

        Mockito.when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));
        Mockito.when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(dto);

        List<ProveedorResponseDTO> resultado = proveedorService.listarProveedores();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Proveedor Test", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe obtener proveedor por ID exitosamente")
    void debeObtenerProveedorPorIdExitosamente() {
        Long proveedorId = 1L;
        ProveedorModel proveedor = ProveedorModel.builder()
                .id(proveedorId).nombre("Proveedor Test").build();
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(proveedorId).nombre("Proveedor Test").build();

        Mockito.when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(proveedor));
        Mockito.when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(dto);
        Mockito.when(contratoRepository.findByProveedorId(proveedorId)).thenReturn(List.of());

        ProveedorResponseDTO resultado = proveedorService.obtenerProveedorPorId(proveedorId);

        assertNotNull(resultado);
        assertEquals("Proveedor Test", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el proveedor no existe al buscar")
    void debeLanzarExcepcionCuandoProveedorNoExisteAlBuscar() {
        Mockito.when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            proveedorService.obtenerProveedorPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe listar proveedores activos exitosamente")
    void debeListarProveedoresActivos() {
        ProveedorModel proveedor = ProveedorModel.builder()
                .id(1L).nombre("Activo").activo(true).build();
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L).nombre("Activo").activo(true).build();

        Mockito.when(proveedorRepository.findAllByActivo()).thenReturn(List.of(proveedor));
        Mockito.when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(dto);

        List<ProveedorResponseDTO> resultado = proveedorService.listarProveedoresActivos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());
    }

    @Test
    @DisplayName("Debe guardar un proveedor exitosamente")
    void debeGuardarProveedorExitosamente() {
        ProveedorRequestDTO dtoEntrada = ProveedorRequestDTO.builder()
                .nombre("Nuevo Proveedor").documentoFiscal("76.123.456-7").build();

        ProveedorModel modelParaGuardar = ProveedorModel.builder()
                .nombre("Nuevo Proveedor").documentoFiscal("76.123.456-7").build();

        ProveedorModel modelGuardado = ProveedorModel.builder()
                .id(1L).nombre("Nuevo Proveedor").documentoFiscal("76.123.456-7").build();

        ProveedorResponseDTO dtoSalida = ProveedorResponseDTO.builder()
                .id(1L).nombre("Nuevo Proveedor").build();

        Mockito.when(proveedorRepository.findByDocumentoFiscal("76.123.456-7")).thenReturn(Optional.empty());
        Mockito.when(proveedorMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(proveedorRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(proveedorMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        ProveedorResponseDTO resultado = proveedorService.guardarProveedor(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nuevo Proveedor", resultado.getNombre());
        Mockito.verify(proveedorRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar DocumentoFiscalAlreadyExistsException al guardar con documento duplicado")
    void debeLanzarExcepcionAlGuardarConDocumentoDuplicado() {
        ProveedorRequestDTO dtoEntrada = ProveedorRequestDTO.builder()
                .nombre("Duplicado").documentoFiscal("76.123.456-7").build();

        ProveedorModel existente = ProveedorModel.builder()
                .id(2L).documentoFiscal("76.123.456-7").build();

        Mockito.when(proveedorRepository.findByDocumentoFiscal("76.123.456-7")).thenReturn(Optional.of(existente));

        assertThrows(DocumentoFiscalAlreadyExistsException.class, () -> {
            proveedorService.guardarProveedor(dtoEntrada);
        });

        Mockito.verify(proveedorRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un proveedor exitosamente")
    void debeActualizarProveedorExitosamente() {
        Long proveedorId = 1L;
        ProveedorModel existente = ProveedorModel.builder()
                .id(proveedorId).nombre("Viejo").documentoFiscal("76.123.456-7").build();

        ProveedorRequestDTO dtoActualizacion = ProveedorRequestDTO.builder()
                .nombre("Nuevo Nombre").documentoFiscal("76.123.456-7").build();

        ProveedorModel actualizado = ProveedorModel.builder()
                .id(proveedorId).nombre("Nuevo Nombre").documentoFiscal("76.123.456-7").build();

        ProveedorResponseDTO dtoSalida = ProveedorResponseDTO.builder()
                .id(proveedorId).nombre("Nuevo Nombre").build();

        Mockito.when(proveedorRepository.findById(proveedorId)).thenReturn(Optional.of(existente));
        Mockito.when(proveedorRepository.findByDocumentoFiscal("76.123.456-7")).thenReturn(Optional.of(existente));
        Mockito.when(proveedorRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(proveedorMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        ProveedorResponseDTO resultado = proveedorService.actualizarProveedor(proveedorId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un proveedor inexistente")
    void debeLanzarExcepcionAlActualizarProveedorInexistente() {
        ProveedorRequestDTO dtoActualizacion = ProveedorRequestDTO.builder()
                .nombre("Test").documentoFiscal("76.123.456-7").build();

        Mockito.when(proveedorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            proveedorService.actualizarProveedor(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un proveedor inexistente")
    void debeLanzarExcepcionAlEliminarProveedorInexistente() {
        Mockito.when(proveedorRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            proveedorService.eliminarProveedor(1L);
        });

        Mockito.verify(proveedorRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un proveedor exitosamente si existe")
    void debeEliminarProveedorExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(proveedorRepository.existsById(idEliminar)).thenReturn(true);

        proveedorService.eliminarProveedor(idEliminar);

        Mockito.verify(proveedorRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
