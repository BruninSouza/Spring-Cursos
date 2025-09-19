package servicocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicocursos.entity.Aula;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<Aula, UUID> {
}
