package org.example.commerce.application.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ComercianteCURequest {


    @NotBlank(message = "El nombre o razón social es obligatorio")
    private String nombreRazonSocial;

    @NotBlank(message = "El municipio es obligatorio")
    private String municipio;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "A|I", message = "El estado debe ser 'A' (Activo) o 'I' (Inactivo)")
    private String estado;


    @Size(max = 20)
    private String telefono;

    @Email
    private String correo;
}
