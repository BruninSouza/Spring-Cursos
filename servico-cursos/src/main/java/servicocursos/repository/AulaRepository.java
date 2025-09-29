package servicocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicocursos.model.Aula;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<Aula, UUID> {
}
