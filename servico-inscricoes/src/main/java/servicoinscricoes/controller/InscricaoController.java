package servicoinscricoes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicoinscricoes.dto.InscricaoDetalhesDTO;
import servicoinscricoes.dto.InscricaoDTO;
import servicoinscricoes.dto.InscricaoStatusUpdateDTO;
import servicoinscricoes.service.InscricaoService;
import servicoinscricoes.dto.AlunoInscritoDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscricoes")
@Tag(name = "Inscrições", description = "Endpoints para a gestão de inscrições")
@SecurityRequirement(name = "bearerAuth")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @Value("${server.port}")
    private String serverPort;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @Operation(summary = "Verifica a saúde do serviço")
    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Inscrições a responder da porta: " + serverPort;
    }

    @Operation(summary = "Cria uma nova inscrição", description = "Inscreve um aluno num curso, validando se os IDs existem e se o aluno já está inscrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada (ex: aluno já inscrito, ou aluno/curso não existe)"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @PostMapping
    public InscricaoDTO criarInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) {
        return inscricaoService.createInscricao(inscricaoDTO);
    }

    @Operation(summary = "Busca as inscrições de um aluno", description = "Retorna uma lista de todas as inscrições de um aluno específico, com detalhes do aluno e do curso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @GetMapping("/aluno/{alunoId}")
    public List<InscricaoDetalhesDTO> buscarInscricoesPorAluno(@PathVariable UUID alunoId) {
        return inscricaoService.getInscricoesByAlunoIdComDetalhes(alunoId);
    }

    @Operation(summary = "Busca os alunos de um curso", description = "Retorna uma lista de todos os alunos inscritos num curso específico, com os seus detalhes e a data da inscrição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @GetMapping("/curso/{cursoId}/alunos")
    public List<AlunoInscritoDTO> buscarAlunosPorCurso(@PathVariable UUID cursoId) {
        return inscricaoService.getAlunosInscritosByCursoId(cursoId);
    }

    @Operation(summary = "Atualiza o status de uma inscrição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada"),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @PutMapping("/{id}/status")
    public InscricaoDTO atualizarStatusInscricao(@PathVariable UUID id, @Valid @RequestBody InscricaoStatusUpdateDTO statusUpdateDTO) {
        return inscricaoService.updateStatus(id, statusUpdateDTO.status());
    }

    @Operation(summary = "Apaga uma inscrição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscrição apagada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarInscricao(@PathVariable UUID id) {
        inscricaoService.deleteInscricao(id);
        return ResponseEntity.noContent().build();
    }
}
