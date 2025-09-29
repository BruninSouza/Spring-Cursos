package servicocursos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicocursos.dto.CursoDetalhesDTO;
import servicocursos.dto.AulaDTO;
import servicocursos.dto.CursoDTO;
import servicocursos.dto.ModuloDTO;
import servicocursos.service.CursoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Cursos", description = "Endpoints para a gestão completa de cursos, módulos e aulas")
@SecurityRequirement(name = "bearerAuth")
public class CursoController {

    private static final Logger logger = LoggerFactory.getLogger(CursoController.class);

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Value("${server.port}")
    private String serverPort;

    @Operation(summary = "Verifica a saúde do serviço", description = "Endpoint de teste para balanceamento de carga.")
    @GetMapping("/ping")
    public String ping() {
        logger.info("Endpoint /ping foi acedido na instância da porta: {}", serverPort);
        return "Serviço de Cursos a responder da porta: " + serverPort;
    }

    @Operation(summary = "Busca detalhes de um curso", description = "Retorna apenas os detalhes essenciais de um curso (ID e titulo). Usado principalmente para comunicação entre serviços.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do curso encontrados"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}/detalhes")
    public CursoDetalhesDTO buscarCursoDetalhesPorId(@PathVariable UUID id) {
        return cursoService.getCursoDetalhesById(id);
    }

    @Operation(summary = "Busca um curso por ID", description = "Retorna os detalhes completos de um curso específico, com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @GetMapping("/{id}")
    public CursoDTO buscarCursoPorId(@PathVariable UUID id) {
        return cursoService.getCursoById(id);
    }

    @Operation(summary = "Lista todos os cursos", description = "Retorna uma lista de todos os cursos registados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso")
    })
    @GetMapping
    public List<CursoDTO> listarCursos() {
        return cursoService.getAllCursos();
    }

    @Operation(summary = "Cria um novo curso", description = "Cria um novo curso no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public CursoDTO criarCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        return cursoService.createCurso(cursoDTO);
    }

    @Operation(summary = "Atualiza um curso existente", description = "Modifica os dados de um curso existente com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado com o ID fornecido"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos no corpo da requisição")
    })
    @PutMapping("/{id}")
    public CursoDTO atualizarCurso(@PathVariable UUID id, @Valid @RequestBody CursoDTO cursoDTO) {
        return cursoService.updateCurso(id, cursoDTO);
    }

    @Operation(summary = "Apaga um curso", description = "Remove permanentemente um curso do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCurso(@PathVariable UUID id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um módulo a um curso", description = "Cria uma tabela de modulo associada a um curso através de cursoId")
    @PostMapping("/{cursoId}/modulos")
    public ModuloDTO adicionarModulo(@PathVariable UUID cursoId, @Valid @RequestBody ModuloDTO moduloDTO) {
        return cursoService.adicionarModulo(cursoId, moduloDTO);
    }

    @Operation(summary = "Adiciona uma aula a um módulo", description = "Cria uma tabela de aula associada a um modulo através de moduloId")
    @PostMapping("/modulos/{moduloId}/aulas")
    public AulaDTO adicionarAula(@PathVariable UUID moduloId, @Valid @RequestBody AulaDTO aulaDTO) {
        return cursoService.adicionarAula(moduloId, aulaDTO);
    }
}

