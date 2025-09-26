package servicocursos.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record AulaDTO(
        UUID id,

        @NotBlank(message = "O título da aula é obrigatório.")
        String titulo,

        @NotBlank(message = "A URL do vídeo é obrigatória.")
        String urlVideo
) {
}
