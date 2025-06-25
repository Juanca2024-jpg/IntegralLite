package org.example.commerce.application.usecase;


import org.example.commerce.application.port.out.MunicipioLovRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LovUseCase {
    private final MunicipioLovRepositoryPort repo;

    public List<String> obtenerMunicipios() {
        return repo.listarMunicipios();
    }
}

