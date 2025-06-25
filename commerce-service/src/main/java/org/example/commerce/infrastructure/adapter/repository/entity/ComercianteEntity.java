
package org.example.commerce.infrastructure.adapter.repository.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "comerciante")
@Getter
@Setter
public class ComercianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comerciante")
    @SequenceGenerator(name = "seq_comerciante",
            sequenceName = "seq_comerciante",
            allocationSize = 1)
    @Column(name = "id_comerciante")
    private Long id;

    @Column(name = "nombre_razon_social")
    private String nombreRazonSocial;

    private String municipio;
    private String telefono;
    private String correo;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    private String estado;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private Long usuarioActualizacion;
}
