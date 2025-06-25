package org.example.commerce.infrastructure.adapter.controller;


import org.example.commerce.application.dto.ComercianteDTO;
import org.example.commerce.application.dto.EstadoDTO;
import org.example.commerce.application.dto.ResumenComercianteDTO;
import org.example.commerce.application.port.in.ComercianteServicePort;
import lombok.RequiredArgsConstructor;
import org.example.commerce.application.usecase.ComercianteUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/comerciantes")
@RequiredArgsConstructor
public class ComercianteController {

    private final ComercianteServicePort service;
    private final ComercianteUseCase useCase;

    @GetMapping
    public List<ComercianteDTO> listar(
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaRegistro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.listar(municipio, nombre, estado, fechaRegistro, page, size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComercianteDTO> obtenerPorId(@PathVariable Long id) {
        ComercianteDTO dto = service.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }
    @PatchMapping("/{id}/estado")
    public ComercianteDTO patchEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoDTO body) {

        return service.cambiarEstado(id, body.getEstado());
    }
    @PostMapping
    public ResponseEntity<ComercianteDTO> crear(@Valid @RequestBody ComercianteDTO d)
    {
        return ResponseEntity.ok(service.crear(d));
    }
    @PutMapping("/{id}")
    public  ResponseEntity<ComercianteDTO> actualizar(@Valid @PathVariable Long id,@Valid @RequestBody ComercianteDTO d)
    {return ResponseEntity.ok(service.actualizar(id,d));}
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id)
    { service.eliminar(id);}

    @GetMapping("/{id}/resumen")
    public ResumenComercianteDTO resumen(@PathVariable Long id) {
        return useCase.getResumen(id);
    }
}
