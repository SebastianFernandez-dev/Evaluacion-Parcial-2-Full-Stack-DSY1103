package com.dsy1103.msproveedores.repository;

import com.dsy1103.msproveedores.model.ContratoModel;
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
class ContratoRepositoryTest {

    @Mock
    private ContratoRepository contratoRepository;

    @Test
    @DisplayName("findById debe retornar contrato cuando existe")
    void findByIdDebeRetornarContrato() {
        ProveedorModel proveedor = ProveedorModel.builder().id(1L).build();
        ContratoModel contrato = ContratoModel.builder()
                .id(1L).numero("CON-001").proveedor(proveedor).build();
        Mockito.when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        Optional<ContratoModel> resultado = contratoRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("CON-001", resultado.get().getNumero());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(contratoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ContratoModel> resultado = contratoRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByProveedorId debe retornar contratos del proveedor")
    void findByProveedorIdDebeFiltrar() {
        ProveedorModel proveedor = ProveedorModel.builder().id(1L).build();
        ContratoModel contrato = ContratoModel.builder()
                .id(1L).numero("CON-001").proveedor(proveedor).build();
        Mockito.when(contratoRepository.findByProveedorId(1L)).thenReturn(List.of(contrato));

        List<ContratoModel> resultados = contratoRepository.findByProveedorId(1L);

        assertEquals(1, resultados.size());
        assertEquals("CON-001", resultados.get(0).getNumero());
    }

    @Test
    @DisplayName("findAll debe retornar todos los contratos")
    void findAllDebeRetornarTodos() {
        ProveedorModel proveedor = ProveedorModel.builder().id(1L).build();
        ContratoModel c1 = ContratoModel.builder().id(1L).numero("CON-001").proveedor(proveedor).build();
        ContratoModel c2 = ContratoModel.builder().id(2L).numero("CON-002").proveedor(proveedor).build();
        Mockito.when(contratoRepository.findAll()).thenReturn(List.of(c1, c2));

        List<ContratoModel> resultados = contratoRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(contratoRepository.existsById(1L)).thenReturn(true);

        assertTrue(contratoRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(contratoRepository.existsById(999L)).thenReturn(false);

        assertFalse(contratoRepository.existsById(999L));
    }
}
