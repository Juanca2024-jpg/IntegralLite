package org.example.commerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Usuario {
    private final Long id;
    private final String nombre;
    private final String correo;
    private final String contrasena;
    private final String rol;
}
