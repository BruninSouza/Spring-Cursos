package main.java.servicoInscricoes.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;

    public Inscricao() {
        this.status = StatusInscricao.ATIVA; // Define ATIVA como status padrão ao criar uma nova inscrição
    }
}

