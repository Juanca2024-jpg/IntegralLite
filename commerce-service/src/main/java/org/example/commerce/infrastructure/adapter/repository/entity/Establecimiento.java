package org.example.commerce.infrastructure.adapter.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ESTABLECIMIENTO")
@Getter @Setter
public class Establecimiento {

    @Id
    @Column(name = "ID_ESTABLECIMIENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ESTABLECIMIENTO_SEQ")
    @SequenceGenerator(name = "ESTABLECIMIENTO_SEQ",
            sequenceName = "SEQ_ESTABLECIMIENTO",
            allocationSize = 1)
    private Long id;

    @Column(name = "NOMBRE_ESTABLECIMIENTO", nullable = false)
    private String nombre;

    @Column(name = "INGRESOS", nullable = false,
            precision = 18, scale = 2)
    private BigDecimal ingresos;

    @Column(name = "NUMERO_EMPLEADOS", nullable = false)
    private Integer empleados;

    /* FK --------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMERCIANTE", nullable = false)
    private ComercianteEntity comerciante;
}
