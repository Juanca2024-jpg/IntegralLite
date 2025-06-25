
import org.example.commerce.application.mapper.ComercianteMapper;
import org.example.commerce.application.port.out.ComercianteRepositoryPort;
import org.example.commerce.application.usecase.ComercianteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ComercianteUseCaseTest {

    @Mock
    ComercianteRepositoryPort repo;

    @Mock
    ComercianteMapper mapper;

    ComercianteUseCase useCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        useCase = new ComercianteUseCase(repo, mapper);
    }

    @Test
    void listarVacioRetornaLista() {
        // Arrange
        String municipio = "";
        String nombre = "";
        String estado = "";
        LocalDate fecha = null;
        int page = 0;
        int size = 10;

        when(repo.findByFiltros(municipio, nombre, estado, fecha, page, size)).thenReturn(List.of());

        // Act
        var result = useCase.listar(municipio, nombre, estado, fecha, page, size);

        // Assert
        assertThat(result).isEmpty();
        verify(repo).findByFiltros(municipio, nombre, estado, fecha, page, size);
    }
}