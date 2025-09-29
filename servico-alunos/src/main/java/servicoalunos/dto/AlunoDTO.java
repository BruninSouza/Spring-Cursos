package servicoalunos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AlunoDTO(
        UUID id,

        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
        String nome,

        @NotBlank(message = "O email não pode estar em branco.")
        @Email(message = "O formato do email é inválido.")
        String email
) {
}
