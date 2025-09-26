package servicoinscricoes.dto;

import jakarta.validation.constraints.NotNull;
import servicoinscricoes.entity.StatusInscricao;

import java.time.LocalDateTime;
import java.util.UUID;

public record InscricaoDTO(
        UUID id,

        @NotNull(message = "O ID do aluno é obrigatório.")
        UUID alunoId,

        @NotNull(message = "O ID do curso é obrigatório.")
        UUID cursoId,

        LocalDateTime dataInscricao,
        StatusInscricao status
) {
}