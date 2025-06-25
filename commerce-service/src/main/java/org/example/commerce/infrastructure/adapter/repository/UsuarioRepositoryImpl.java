package org.example.commerce.infrastructure.adapter.repository;

import org.example.commerce.application.port.out.UsuarioRepositoryPort;
import org.example.commerce.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository @RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepositoryPort {
    private final SpringDataUsuarioJpaRepo jpa;

    @Override
    public Optional<Usuario> findByCorreo(String correo){
        return jpa.findByCorreo(correo).map(e ->
                new Usuario(e.getId(),e.getNombre(),e.getCorreo(),e.getContrasena(),e.getRol()));
    }
}
