package org.example.commerce.infrastructure.config;

import org.example.commerce.application.mapper.ComercianteMapper;
import org.example.commerce.application.port.in.ComercianteServicePort;
import org.example.commerce.application.port.out.ComercianteRepositoryPort;
import org.example.commerce.application.usecase.ComercianteUseCase;
import org.example.commerce.infrastructure.adapter.repository.EstablecimientoRepository;
import org.springframework.context.annotation.*;

@Configuration
public class UseCaseConfig {
    @Bean
    public ComercianteServicePort comercianteService(ComercianteRepositoryPort repo, EstablecimientoRepository
            establecimientoRepository,
                                                     ComercianteMapper mapper){
        return new ComercianteUseCase(repo,establecimientoRepository,mapper);
    }
}
