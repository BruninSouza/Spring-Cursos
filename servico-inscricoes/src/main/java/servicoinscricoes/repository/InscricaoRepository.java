package servicoinscricoes.repository;

import servicoinscricoes.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {
    List<Inscricao> findByAlunoId(UUID alunoId);
}