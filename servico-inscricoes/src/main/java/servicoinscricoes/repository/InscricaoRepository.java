package servicoinscricoes.repository;

import servicoinscricoes.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {
    List<Inscricao> findByAlunoId(UUID alunoId);
    boolean existsByAlunoIdAndCursoId(UUID alunoId, UUID cursoId);
    List<Inscricao> findByCursoId(UUID cursoId);
}