package org.example.commerce.infrastructure.adapter.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReporteRow {
    private final String nombreRazonSocial;
    private final String municipio;
    private final String telefono;
    private final String correo;
    private final LocalDate fechaRegistro;
    private final String estado;
    private final int cantidadEstablecimientos;
    private final BigDecimal totalIngresos;
    private final int cantidadEmpleados;
}
