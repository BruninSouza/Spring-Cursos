package servicocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicocursos.entity.Curso;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {
}
