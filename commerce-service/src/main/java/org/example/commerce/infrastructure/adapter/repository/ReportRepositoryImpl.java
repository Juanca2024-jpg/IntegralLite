package org.example.commerce.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.example.commerce.application.port.out.ReportRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryPort {

    private final JdbcTemplate jdbc;
    private SimpleJdbcCall call;

    @PostConstruct
    void init() {
        RowMapper<ReporteRow> mapper = this::map;               // método map ⇩

        call = new SimpleJdbcCall(jdbc)
                .withCatalogName("COMERCIO_PKG")
                .withFunctionName("FN_COMERCIANTES_ACTIVOS")
                .declareParameters(new SqlOutParameter("RETURN", Types.REF_CURSOR, mapper));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReporteRow> obtenerComerciantesActivos() {
        return (List<ReporteRow>) call.executeFunction(List.class);
    }

    /* ---------- mapea cada fila ---------- */
    private ReporteRow map(ResultSet rs, int rowNum) throws SQLException {
        return new ReporteRow(
                rs.getString("nombre_razon_social"),
                rs.getString("municipio"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getDate("fecha_registro").toLocalDate(),
                rs.getString("estado"),
                rs.getInt("cantidad_establecimientos"),
                rs.getBigDecimal("total_ingresos"),
                rs.getInt("cantidad_empleados")
        );
    }
}
