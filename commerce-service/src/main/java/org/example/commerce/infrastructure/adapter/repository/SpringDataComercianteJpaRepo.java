
package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.infrastructure.adapter.repository.entity.ComercianteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpringDataComercianteJpaRepo
        extends JpaRepository<ComercianteEntity, Long>,
        JpaSpecificationExecutor<ComercianteEntity> {

    Page<ComercianteEntity> findByMunicipioContainingIgnoreCase(String municipio, Pageable pageable);
}
