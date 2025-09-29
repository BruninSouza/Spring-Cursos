package servicoalunos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicoalunos.dto.AlunoDetalhesDTO;
import servicoalunos.dto.AlunoDTO;
import servicoalunos.service.AlunoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerir os dados dos alunos")
@SecurityRequirement(name = "bearerAuth")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Value("${server.port}")
    private String serverPort;

    @Operation(summary = "Verifica se o serviço está funcionando", description = "Endpoint de teste que retorna a porta em que a instância do serviço está a ser executada.")
    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Alunos a responder da porta: " + serverPort;
    }

    @Operation(summary = "Lista todos os alunos", description = "Retorna uma lista de todos os alunos registados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @GetMapping
    public List<AlunoDTO> listarAlunos() {
        return alunoService.getAllAlunos();
    }

    @Operation(summary = "Busca um aluno por ID ", description = "Retorna os detalhes completos de um aluno específico, com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @GetMapping("/{id}")
    public AlunoDTO buscarAlunoPorId(@PathVariable UUID id) {
        return alunoService.getAlunoById(id);
    }

    @Operation(summary = "Atualiza um aluno", description = "Modifica os dados de um aluno existente com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos no corpo da requisição"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @PutMapping("/{id}")
    public AlunoDTO atualizarAluno(@PathVariable UUID id, @Valid @RequestBody AlunoDTO alunoDTO) {
        return alunoService.updateAluno(id, alunoDTO);
    }

    @Operation(summary = "Busca detalhes de um aluno", description = "Retorna apenas os detalhes essenciais de um aluno (ID e nome). Usado principalmente para comunicação entre serviços.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do aluno encontrados"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @GetMapping("/{id}/detalhes")
    public AlunoDetalhesDTO buscarAlunoDetalhesPorId(@PathVariable UUID id) {
        return alunoService.getAlunoDetalhesById(id);
    }

    @Operation(summary = "Apaga um aluno", description = "Remove permanentemente um aluno do sistema com base no seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer autenticação.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAluno(@PathVariable UUID id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }
}