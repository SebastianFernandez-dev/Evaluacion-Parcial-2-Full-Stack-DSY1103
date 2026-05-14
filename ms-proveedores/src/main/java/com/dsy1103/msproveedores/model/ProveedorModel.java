package com.dsy1103.msproveedores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="proveedor")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre", nullable=false, length=80)
    private String nombre;

    @Column(name="razon_social", nullable=false, length=120)
    private String razonSocial;

    @Column(name="documento_fiscal", nullable=false, length=30)
    private String documentoFiscal;

    @Column(name="correo_contacto", nullable=false, length=80, unique=true)
    private String correoContacto;

    @Column(name="ciudad", nullable=false, length=80)
    private String ciudad;

    @Column(name="calificacion", nullable=false)
    private Integer calificacion;

    @Column(name="activo", nullable=false)
    private Boolean activo;

    @Column(name="fecha_registro", nullable=false)
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy="proveedor",
            cascade=CascadeType.ALL,
            fetch=FetchType.LAZY)
    private List<ContratoModel> listaContrato = new ArrayList<>();
}
