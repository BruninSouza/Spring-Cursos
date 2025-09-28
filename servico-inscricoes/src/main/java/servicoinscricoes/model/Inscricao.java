package servicoinscricoes.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID alunoId;
    private UUID cursoId;
    private LocalDateTime dataInscricao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusInscricao status = StatusInscricao.ATIVA;
}
