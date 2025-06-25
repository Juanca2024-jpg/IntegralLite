package org.example.commerce.application.port.out;

import org.example.commerce.domain.model.Comerciante;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ComercianteRepositoryPort {
    Comerciante save(Comerciante c);
    Optional<Comerciante> findById(Long id);
    void deleteById(Long id);
    List<Comerciante> findByFiltros(String municipio, String nombre, String estado,
                                    LocalDate fechaRegistro, int page, int size);
}