package servicocursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CursoDTO(
        UUID id,

        @NotBlank(message = "O título não pode estar em branco.")
        @Size(min = 5, message = "O título deve ter pelo menos 5 caracteres.")
        String titulo,

        @NotBlank(message = "A descrição não pode estar em branco.")
        String descricao,

        @NotBlank(message = "O nome do instrutor não pode estar em branco.")
        String instrutor
) {
}
