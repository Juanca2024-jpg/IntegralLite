package org.example.commerce.infrastructure.adapter.controller;

import org.example.commerce.application.usecase.LovUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lov")
@RequiredArgsConstructor
public class LovController {

    private final LovUseCase useCase;


    @GetMapping("/municipios")
    public ResponseEntity<List<String>> municipios() {
        return ResponseEntity.ok(useCase.obtenerMunicipios());
    }
}
