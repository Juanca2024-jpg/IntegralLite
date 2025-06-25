package org.example.commerce.application.usecase;



import org.example.commerce.application.dto.ComercianteDTO;
import org.example.commerce.application.dto.ResumenComercianteDTO;
import org.example.commerce.application.mapper.ComercianteMapper;
import org.example.commerce.application.port.in.ComercianteServicePort;
import org.example.commerce.application.port.out.ComercianteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.example.commerce.infrastructure.adapter.repository.EstablecimientoRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ComercianteUseCase implements ComercianteServicePort {

    private final ComercianteRepositoryPort repo;
    private final EstablecimientoRepository establecimientoRepo;
    private final ComercianteMapper mapper;


    @Override
    public ComercianteDTO cambiarEstado(Long id, String nuevoEstado) {
        var ent = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Comerciante " + id + " no existe"));
        ent.setEstado(nuevoEstado);
        return mapper.toDto(repo.save(ent));
    }
    @Override
    public ComercianteDTO obtenerPorId(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante " + id +
                        " no encontrado")));
    }

    @Override
    public List<ComercianteDTO> listar(String municipio, String nombreRazonSocial, String estado, LocalDate fechaRegistro, int page, int size) {
        return repo.findByFiltros(municipio, nombreRazonSocial, estado, fechaRegistro, page, size)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComercianteDTO crear(ComercianteDTO dto){
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public ComercianteDTO actualizar(Long id,ComercianteDTO dto){
        var existing=repo.findById(id).orElseThrow();
        if(dto.getCorreo() == null && dto.getTelefono() == null ) {
            var actualizado = new org.example.commerce.domain.model.Comerciante(
                    existing.getId(),
                    dto.getNombreRazonSocial(),
                    dto.getMunicipio(),
                    existing.getTelefono(),
                    existing.getCorreo(),
                    existing.getFechaRegistro(),
                    dto.getEstado()
            );
            return mapper.toDto(repo.save(actualizado));
        }if(dto.getCorreo() == null ) {
            var actualizado = new org.example.commerce.domain.model.Comerciante(
                    existing.getId(),
                    dto.getNombreRazonSocial(),
                    dto.getMunicipio(),
                    dto.getTelefono(),
                    existing.getCorreo(),
                    existing.getFechaRegistro(),
                    dto.getEstado()
            );
            return mapper.toDto(repo.save(actualizado));
        }if(dto.getTelefono() == null ) {
            var actualizado = new org.example.commerce.domain.model.Comerciante(
                    existing.getId(),
                    dto.getNombreRazonSocial(),
                    dto.getMunicipio(),
                    existing.getTelefono(),
                    dto.getCorreo(),
                    existing.getFechaRegistro(),
                    dto.getEstado()
            );
            return mapper.toDto(repo.save(actualizado));
        }else {
            var actualizado = new org.example.commerce.domain.model.Comerciante(
                    existing.getId(),
                    dto.getNombreRazonSocial(),
                    dto.getMunicipio(),
                    dto.getTelefono(),
                    dto.getCorreo(),
                    existing.getFechaRegistro(),
                    dto.getEstado()
            );
            return mapper.toDto(repo.save(actualizado));
        }
    }

    @Override
    public void eliminar(Long id){ repo.deleteById(id);}

    @Override
    public ResumenComercianteDTO getResumen(long idComerciante) {
        Object[] r = (Object[]) establecimientoRepo
                .sumIngresosAndEmpleados(idComerciante)
                .orElse(new Object[]{BigDecimal.ZERO, 0});

        return new ResumenComercianteDTO(
                (BigDecimal) r[0],
                ((Number) r[1]).intValue());
    }

}