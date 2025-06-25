package org.example.commerce.application.port.out;

import org.example.commerce.infrastructure.adapter.repository.ReporteRow;

import java.sql.ResultSet;
import java.util.List;

public interface ReportRepositoryPort {
    List<ReporteRow> obtenerComerciantesActivos();   // ← ahora List<ReporteRow>
}