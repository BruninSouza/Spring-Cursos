package servicoalunos.dto;

import java.util.UUID;

public record AlunoDetalhesDTO(
        UUID id,
        String nome
) {}