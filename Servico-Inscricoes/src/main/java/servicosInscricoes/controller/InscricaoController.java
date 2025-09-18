package main.java.servicoinscricoes.controller;

import main.java.servicoinscricoes.entity.Inscricao;
import main.java.servicoinscricoes.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gerenciar as operações de Inscrição.
 */
@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    /**
     * Endpoint para criar uma nova inscrição.
     * Recebe um objeto Inscricao no corpo da requisição.
     * @param inscricao Contém o alunoId e o cursoId.
     * @return A inscrição salva.
     */
    @PostMapping
    public Inscricao criarInscricao(@RequestBody Inscricao inscricao) {
        // Em um sistema real, aqui você faria chamadas para os outros serviços
        // para validar se o alunoId e o cursoId existem antes de salvar.
        // Para este projeto, confiamos que os IDs são válidos.
        return inscricaoRepository.save(inscricao);
    }

    /**
     * Endpoint para listar todas as inscrições de um aluno específico.
     * @param alunoId O ID do aluno.
     * @return Uma lista de inscrições.
     */
    @GetMapping("/aluno/{alunoId}")
    public List<Inscricao> listarInscricoesPorAluno(@PathVariable UUID alunoId) {
        return inscricaoRepository.findByAlunoId(alunoId);
    }

    /**
     * Endpoint para listar todos os alunos inscritos em um curso específico.
     * @param cursoId O ID do curso.
     * @return Uma lista de inscrições.
     */
    @GetMapping("/curso/{cursoId}")
    public List<Inscricao> listarInscricoesPorCurso(@PathVariable UUID cursoId) {
        return inscricaoRepository.findByCursoId(cursoId);
    }

    /**
     * Endpoint para buscar uma inscrição específica pelo seu ID.
     * @param id O ID da inscrição.
     * @return A inscrição encontrada ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> buscarInscricaoPorId(@PathVariable UUID id) {
        return inscricaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}