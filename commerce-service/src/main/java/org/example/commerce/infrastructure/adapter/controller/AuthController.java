package org.example.commerce.infrastructure.adapter.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.example.commerce.application.port.out.UsuarioRepositoryPort;
import org.example.commerce.infrastructure.adapter.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepositoryPort repo;
    private final JwtTokenProvider provider;

    @Getter
    @Setter
    public static class LoginRQ {
        private String correo;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginRS {
        private final String token;


    }

    @PostMapping("/login")
    public ResponseEntity<LoginRS> login(@RequestBody LoginRQ rq) {
        var u = repo.findByCorreo(rq.getCorreo()).orElse(null);

        if (u == null || !u.getContrasena().equals(rq.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String token = provider.generarToken(u.getCorreo(), u.getRol());
        return ResponseEntity.ok(new LoginRS(token));
    }
}
