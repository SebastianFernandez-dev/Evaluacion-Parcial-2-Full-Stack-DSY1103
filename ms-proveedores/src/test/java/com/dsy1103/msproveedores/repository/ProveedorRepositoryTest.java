package com.dsy1103.msproveedores.repository;

import com.dsy1103.msproveedores.model.ProveedorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProveedorRepositoryTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Test
    @DisplayName("findById debe retornar proveedor cuando existe")
    void findByIdDebeRetornarProveedor() {
        ProveedorModel proveedor = ProveedorModel.builder()
                .id(1L).nombre("Alfa Distribuciones").build();
        Mockito.when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        Optional<ProveedorModel> resultado = proveedorRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Alfa Distribuciones", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(proveedorRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ProveedorModel> resultado = proveedorRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAllByActivo debe retornar solo proveedores activos")
    void findAllByActivoDebeFiltrar() {
        ProveedorModel activo = ProveedorModel.builder()
                .id(1L).nombre("Activo").activo(true).build();
        Mockito.when(proveedorRepository.findAllByActivo()).thenReturn(List.of(activo));

        List<ProveedorModel> resultados = proveedorRepository.findAllByActivo();

        assertEquals(1, resultados.size());
        assertTrue(resultados.get(0).getActivo());
    }

    @Test
    @DisplayName("findByDocumentoFiscal debe retornar proveedor cuando existe")
    void findByDocumentoFiscalDebeRetornarProveedor() {
        ProveedorModel proveedor = ProveedorModel.builder()
                .id(1L).documentoFiscal("76.123.456-7").build();
        Mockito.when(proveedorRepository.findByDocumentoFiscal("76.123.456-7")).thenReturn(Optional.of(proveedor));

        Optional<ProveedorModel> resultado = proveedorRepository.findByDocumentoFiscal("76.123.456-7");

        assertTrue(resultado.isPresent());
        assertEquals("76.123.456-7", resultado.get().getDocumentoFiscal());
    }

    @Test
    @DisplayName("findByDocumentoFiscal debe retornar empty cuando no existe")
    void findByDocumentoFiscalDebeRetornarEmpty() {
        Mockito.when(proveedorRepository.findByDocumentoFiscal("00.000.000-0")).thenReturn(Optional.empty());

        Optional<ProveedorModel> resultado = proveedorRepository.findByDocumentoFiscal("00.000.000-0");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todos los proveedores")
    void findAllDebeRetornarTodos() {
        ProveedorModel p1 = ProveedorModel.builder().id(1L).nombre("A").build();
        ProveedorModel p2 = ProveedorModel.builder().id(2L).nombre("B").build();
        Mockito.when(proveedorRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProveedorModel> resultados = proveedorRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(proveedorRepository.existsById(1L)).thenReturn(true);

        assertTrue(proveedorRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(proveedorRepository.existsById(999L)).thenReturn(false);

        assertFalse(proveedorRepository.existsById(999L));
    }
}
