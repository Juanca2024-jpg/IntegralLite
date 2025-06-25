package org.example.commerce.application.port.in;

import org.example.commerce.application.dto.ComercianteDTO;
import org.example.commerce.application.dto.ResumenComercianteDTO;

import java.time.LocalDate;
import java.util.List;

public interface ComercianteServicePort {
    List<ComercianteDTO> listar(String municipio, String nombreRazonSocial, String estado, LocalDate fechaRegistro,
                                int page, int size);

    ComercianteDTO crear(ComercianteDTO dto);
    ComercianteDTO actualizar(Long id,ComercianteDTO dto);
    ComercianteDTO obtenerPorId(Long id);
    ComercianteDTO cambiarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
    ResumenComercianteDTO getResumen (long idComerciante);

}