package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.application.port.out.MunicipioLovRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MunicipioLovRepositoryImpl implements MunicipioLovRepositoryPort {

    private final EntityManager em;

    @Override
    @Cacheable("lovMunicipios")
    public List<String> listarMunicipios() {
        return em.createQuery(
                "SELECT DISTINCT c.municipio FROM ComercianteEntity c ORDER BY c.municipio",
                String.class
        ).getResultList();
    }
}
