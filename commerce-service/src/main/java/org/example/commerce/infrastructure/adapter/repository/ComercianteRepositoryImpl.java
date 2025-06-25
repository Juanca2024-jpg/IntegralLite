package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.application.port.out.ComercianteRepositoryPort;
import org.example.commerce.domain.model.Comerciante;
import org.example.commerce.infrastructure.adapter.repository.entity.ComercianteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.commerce.infrastructure.adapter.repository.ComercianteSpecification.*;

@Repository @RequiredArgsConstructor
public class ComercianteRepositoryImpl implements ComercianteRepositoryPort {

    private final SpringDataComercianteJpaRepo jpa;

    @Override
    public Comerciante save(Comerciante c){
        return toDomain(jpa.save(toEntity(c)));
    }

    @Override public Optional<Comerciante> findById(Long id){
        return jpa.findById(id).map(this::toDomain);
    }

    @Override public void deleteById(Long id){ jpa.deleteById(id); }

    @Override
    public List<Comerciante> findByFiltros(String municipio, String nombre, String estado, LocalDate fechaRegistro, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<ComercianteEntity> spec = Specification
                .where(municipioLike(municipio))
                .and(nombreLike(nombre))
                .and(estadoEquals(estado))
                .and(fechaEquals(fechaRegistro));

        return jpa.findAll(spec, pageable)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }



    private Comerciante toDomain(ComercianteEntity e){
        return new Comerciante(e.getId(),e.getNombreRazonSocial(),e.getMunicipio(),
                e.getTelefono(),e.getCorreo(),e.getFechaRegistro(),e.getEstado());
    }
    private ComercianteEntity toEntity(Comerciante d){
        var e=new ComercianteEntity();
        e.setId(d.getId()); e.setNombreRazonSocial(d.getNombreRazonSocial());
        e.setMunicipio(d.getMunicipio()); e.setTelefono(d.getTelefono());
        e.setCorreo(d.getCorreo()); e.setFechaRegistro(d.getFechaRegistro());
        e.setEstado(d.getEstado());
        return e;
    }
}
