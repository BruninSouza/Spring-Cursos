package servicocursos.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ModuloDTO(
        UUID id,

        @NotBlank(message = "O título do módulo é obrigatório.")
        String titulo
) {
}
