package org.example.commerce.infrastructure.adapter.security;

import org.example.commerce.application.port.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepositoryPort repo;
    @Override
    public UserDetails loadUserByUsername(String username){
        var u=repo.findByCorreo(username)
                .orElseThrow(()->new UsernameNotFoundException("Not found"));
        return User.withUsername(u.getCorreo())
                .password("{noop}"+u.getContrasena())
                .roles(u.getRol())
                .build();
    }
}
