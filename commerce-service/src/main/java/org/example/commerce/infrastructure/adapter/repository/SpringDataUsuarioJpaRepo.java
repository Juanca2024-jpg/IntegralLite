package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.infrastructure.adapter.repository.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataUsuarioJpaRepo extends JpaRepository<UsuarioEntity,Long>{
    Optional<UsuarioEntity> findByCorreo(String correo);
}