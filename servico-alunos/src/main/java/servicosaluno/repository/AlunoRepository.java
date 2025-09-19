package servicosaluno.repository;

import servicosaluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {}
