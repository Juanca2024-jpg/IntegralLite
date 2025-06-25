package org.example.commerce.application.port.out;

import org.example.commerce.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findByCorreo(String correo);
}
