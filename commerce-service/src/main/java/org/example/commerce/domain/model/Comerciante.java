
package org.example.commerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Comerciante {
    private  Long id;
    private  String nombreRazonSocial;
    private  String municipio;
    private  String telefono;
    private  String correo;
    private  LocalDate fechaRegistro;
    private  String estado;

}
