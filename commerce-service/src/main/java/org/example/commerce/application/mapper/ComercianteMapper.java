package org.example.commerce.application.mapper;

import org.example.commerce.application.dto.ComercianteCURequest;
import org.example.commerce.application.dto.ComercianteDTO;
import org.example.commerce.domain.model.Comerciante;

public interface ComercianteMapper {
    ComercianteDTO toDto(Comerciante entity);

    Comerciante toEntity(ComercianteDTO dto);


    Comerciante toEntity(ComercianteCURequest rq);


    void update(Comerciante target, ComercianteCURequest rq);
}
