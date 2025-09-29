package servicoinscricoes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import servicoinscricoes.model.StatusInscricao;
import java.time.LocalDateTime;
import java.util.UUID;

public record InscricaoDetalhesDTO(
        UUID id,
        String alunoNome,
        String cursoTitulo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataInscricao,

        StatusInscricao status
) {}
