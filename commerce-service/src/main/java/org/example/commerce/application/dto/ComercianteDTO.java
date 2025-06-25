package org.example.commerce.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.*;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ComercianteDTO {

    private Long id;

    @NotBlank(message = "nombreRazonSocial es obligatorio")
    @Size(max = 150)
    private String nombreRazonSocial;

    @NotBlank(message = "municipio es obligatorio")
    @Size(max = 100)
    private String municipio;


    private String telefono;

    @Email(message = "correo inválido")
    @Size(max = 150)
    private String correo;
    @NotNull(message = "fechaRegistro es obligatoria")
    private LocalDate fechaRegistro;

    @Pattern(regexp = "A|I", message = "estado debe ser A o I")
    private String estado;
}
