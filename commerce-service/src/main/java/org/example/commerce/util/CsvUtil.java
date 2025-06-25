
package org.example.commerce.util;

import org.example.commerce.infrastructure.adapter.repository.ReporteRow;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class CsvUtil {

    private CsvUtil() {}

    public static byte[] toCsv(List<ReporteRow> rows) {

        StringBuilder sb = new StringBuilder();

        final String SEPARADOR = "|";

        sb.append("nombreRazonSocial|municipio|telefono|correo|fechaRegistro|estado|")
                .append("cantidadEstablecimientos|totalIngresos|cantidadEmpleados\n");

        for (ReporteRow r : rows) {
            sb.append(escape(r.getNombreRazonSocial())).append(SEPARADOR)
                    .append(escape(r.getMunicipio())).append(SEPARADOR)
                    .append(escape(r.getTelefono())).append(SEPARADOR)
                    .append(escape(r.getCorreo())).append(SEPARADOR)
                    .append(r.getFechaRegistro()).append(SEPARADOR)
                    .append(r.getEstado()).append(SEPARADOR)
                    .append(r.getCantidadEstablecimientos()).append(SEPARADOR)
                    .append(r.getTotalIngresos()).append(SEPARADOR)
                    .append(r.getCantidadEmpleados()).append('\n');
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.contains(",") ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }
}
