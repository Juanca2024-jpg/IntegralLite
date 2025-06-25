package org.example.commerce.infrastructure.adapter.repository.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity @Table(name="usuario")
@Getter @Setter
public class UsuarioEntity {
    @Id @Column(name="id_usuario")      private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
}