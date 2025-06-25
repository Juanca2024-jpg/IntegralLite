package org.example.commerce.application.mapper;

import org.example.commerce.application.dto.ComercianteCURequest;
import org.example.commerce.application.dto.ComercianteDTO;
import org.example.commerce.domain.model.Comerciante;
import org.springframework.stereotype.Component;

@Component
public class ComercianteMapperImpl implements ComercianteMapper {

    @Override
    public ComercianteDTO toDto(Comerciante e) {
        if (e == null) return null;
        ComercianteDTO d = new ComercianteDTO();
        d.setId(e.getId());
        d.setNombreRazonSocial(e.getNombreRazonSocial());
        d.setMunicipio(e.getMunicipio());
        d.setTelefono(e.getTelefono());
        d.setCorreo(e.getCorreo());
        d.setFechaRegistro(e.getFechaRegistro());
        d.setEstado(e.getEstado());
        return d;
    }

    @Override
    public Comerciante toEntity(ComercianteDTO d) {
        if (d == null) return null;
        return new Comerciante(
                d.getId(),
                d.getNombreRazonSocial(),
                d.getMunicipio(),
                d.getTelefono(),
                d.getCorreo(),
                d.getFechaRegistro(),
                d.getEstado()
        );
    }

    @Override
    public Comerciante toEntity(ComercianteCURequest rq) {
        if (rq == null) return null;
        return new Comerciante(
                null,
                rq.getNombreRazonSocial(),
                rq.getMunicipio(),
                rq.getTelefono(),
                rq.getCorreo(),
                rq.getFechaRegistro(),
                rq.getEstado()
        );
    }


    @Override
    public void update(Comerciante target, ComercianteCURequest rq) {
        if (target == null || rq == null) return;

        target.setNombreRazonSocial(rq.getNombreRazonSocial());
        target.setMunicipio(rq.getMunicipio());
        target.setTelefono(rq.getTelefono());
        target.setCorreo(rq.getCorreo());
        target.setEstado(rq.getEstado());
    }
}