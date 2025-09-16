package main.java.servicosAluno.repository;

import com.servicosAluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {}
