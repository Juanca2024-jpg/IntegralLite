package org.example.commerce.infrastructure.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.example.commerce.application.port.out.ReportRepositoryPort;
import org.example.commerce.util.CsvUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepositoryPort repo;

    @GetMapping(value = "/comerciantes.csv", produces = "text/csv")
    public ResponseEntity<byte[]> descargar() {

        var lista = repo.obtenerComerciantesActivos();


        byte[] csvBytes = CsvUtil.toCsv(lista);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=comerciantes.csv")
                .contentType(MediaType.valueOf("text/csv"))
                .body(csvBytes);
    }
}
