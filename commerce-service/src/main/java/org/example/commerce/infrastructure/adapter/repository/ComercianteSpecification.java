package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.infrastructure.adapter.repository.entity.ComercianteEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ComercianteSpecification {

    public static Specification<ComercianteEntity> municipioLike(String municipio) {
        return (root, query, cb) ->
                municipio == null || municipio.isBlank() ? null :
                        cb.like(cb.lower(root.get("municipio")), "%" + municipio.toLowerCase() + "%");
    }

    public static Specification<ComercianteEntity> nombreLike(String nombre) {
        return (root, query, cb) ->
                nombre == null || nombre.isBlank() ? null :
                        cb.like(cb.lower(root.get("nombreRazonSocial")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<ComercianteEntity> estadoEquals(String estado) {
        return (root, query, cb) ->
                estado == null || estado.isBlank() ? null :
                        cb.equal(cb.lower(root.get("estado")), estado.toLowerCase());
    }

    public static Specification<ComercianteEntity> fechaEquals(LocalDate fecha) {
        return (root, query, cb) -> {
            if (fecha == null) return null;
            return cb.equal(
                    cb.function("TRUNC", LocalDate.class, root.get("fechaRegistro")),
                    fecha
            );
        };
    }



}


