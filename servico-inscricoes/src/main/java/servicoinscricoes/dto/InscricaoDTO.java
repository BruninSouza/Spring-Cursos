package servicoinscricoes.dto;

import jakarta.validation.constraints.NotNull;
import servicoinscricoes.model.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record InscricaoDTO(
        UUID id,

        @NotNull(message = "O ID do aluno é obrigatório.")
        UUID alunoId,

        @NotNull(message = "O ID do curso é obrigatório.")
        UUID cursoId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataInscricao,

        StatusInscricao status
) {
}