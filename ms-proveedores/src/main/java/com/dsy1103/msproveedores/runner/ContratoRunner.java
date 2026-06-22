package com.dsy1103.msproveedores.runner;

import com.dsy1103.msproveedores.model.ContratoModel;
import com.dsy1103.msproveedores.repository.ContratoRepository;
import com.dsy1103.msproveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2)
public class ContratoRunner implements CommandLineRunner {

    @Autowired
    ContratoRepository contratoRepository;
    @Autowired
    ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!contratoRepository.existsById(1L)) {
            contratoRepository.save(ContratoModel.builder()
                            .id(null)
                            .numero("CON-2026-001")
                            .tipo("Suministros")
                            .valor(45.50D)
                            .fechaInicio(LocalDate.of(2026, 1, 1))
                            .fechaFin(LocalDate.of(2027, 1, 1))
                            .vigente(true)
                            .observaciones("Contrato anual para la entrega de papelería e insumos de oficina.")
                            .proveedor(proveedorRepository.findById(1L).orElse(null))
                            .build());
        }

        if (!contratoRepository.existsById(2L)) {
            contratoRepository.save(ContratoModel.builder()
                    .id(null)
                    .numero("CON-2026-002")
                    .tipo("Mantenimiento")
                    .valor(89.90D)
                    .fechaInicio(LocalDate.of(2026, 3, 15))
                    .fechaFin(LocalDate.of(2026, 12, 31))
                    .vigente(true)
                    .observaciones("Servicio técnico y mantención preventiva para los aires acondicionados.")
                    .proveedor(proveedorRepository.findById(2L).orElse(null))
                    .build());
        }

        if (!contratoRepository.existsById(3L)) {
            contratoRepository.save(ContratoModel.builder()
                    .id(null)
                    .numero("CON-2026-003")
                    .tipo("Consultoría")
                    .valor(15.00D)
                    .fechaInicio(LocalDate.of(2026, 5, 2))
                    .fechaFin(LocalDate.of(2026, 8, 30))
                    .vigente(false)
                    .observaciones("Asesoría legal externa para la revisión de estatutos internos de la empresa.")
                    .proveedor(proveedorRepository.findById(1L).orElse(null))
                    .build());
        }

        System.out.println("DATOS iniciales de CONTRATO cargados CORRECTAMENTE");
    }
}
