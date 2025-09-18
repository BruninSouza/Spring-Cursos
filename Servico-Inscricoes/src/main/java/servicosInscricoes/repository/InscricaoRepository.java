package main.java.servicoinscricoes.repository;

import main.java.servicoinscricoes.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Interface de repositório para a entidade Inscricao.
 * Fornece os métodos de CRUD e permite a criação de consultas personalizadas.
 */
@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {

    /**
     * O Spring Data JPA cria automaticamente a consulta SQL correspondente a este método.
     * Ele buscará todas as inscrições onde o campo 'alunoId' corresponde ao parâmetro fornecido.
     *
     * @param alunoId O ID do aluno para o qual se deseja buscar as inscrições.
     * @return Uma lista de inscrições para o aluno especificado.
     */
    List<Inscricao> findByAlunoId(UUID alunoId);

    /**
     * De forma similar, este método busca todas as inscrições para um curso específico.
     *
     * @param cursoId O ID do curso.
     * @return Uma lista de inscrições para o curso especificado.
     */
    List<Inscricao> findByCursoId(UUID cursoId);
}
