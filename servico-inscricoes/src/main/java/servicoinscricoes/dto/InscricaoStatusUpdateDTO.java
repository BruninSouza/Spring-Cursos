package servicoinscricoes.dto;

import jakarta.validation.constraints.NotNull;
import servicoinscricoes.model.StatusInscricao;

public record InscricaoStatusUpdateDTO(
        @NotNull(message = "O status da inscrição é obrigatório.")
        StatusInscricao status
) {
}