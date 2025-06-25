package org.example.commerce.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public final class ResumenComercianteDTO {

    private final BigDecimal totalIngresos;
    private final int        totalEmpleados;


}
