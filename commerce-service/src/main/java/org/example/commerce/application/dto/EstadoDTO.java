package org.example.commerce.application.dto;


import lombok.*;

import javax.validation.constraints.Pattern;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EstadoDTO {
    @Pattern(regexp = "A|I")
    private String estado;
}
