package servicocursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicocursos.entity.Modulo;

import java.util.UUID;

public interface ModuloRepository extends JpaRepository<Modulo, UUID> {
}
