package servicocursos.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CursoController {

    private static final Logger logger = LoggerFactory.getLogger(CursoController.class);

    @Autowired
    private CursoService cursoService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        logger.info("Endpoint /ping foi acedido na instância da porta: {}", serverPort);
        return "Serviço de Cursos a responder da porta: " + serverPort;
    }

    @GetMapping("/{id}/detalhes")
    public CursoDetalhesDTO buscarCursoDetalhesPorId(@PathVariable UUID id) {
        return cursoService.getCursoDetalhesById(id);
    }

    @PostMapping
    public CursoDTO criarCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        return cursoService.createCurso(cursoDTO);
    }

    @GetMapping
    public List<CursoDTO> listarCursos() {
        return cursoService.getAllCursos();
    }

    @GetMapping("/{id}")
    public CursoDTO buscarCursoPorId(@PathVariable UUID id) {
        return cursoService.getCursoById(id);
    }

    @PutMapping("/{id}")
    public CursoDTO atualizarCurso(@PathVariable UUID id, @Valid @RequestBody CursoDTO cursoDTO) {
        return cursoService.updateCurso(id, cursoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCurso(@PathVariable UUID id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cursoId}/modulos")
    public ModuloDTO adicionarModulo(@PathVariable UUID cursoId, @Valid @RequestBody ModuloDTO moduloDTO) {
        return cursoService.adicionarModulo(cursoId, moduloDTO);
    }

    @PostMapping("/modulos/{moduloId}/aulas")
    public AulaDTO adicionarAula(@PathVariable UUID moduloId, @Valid @RequestBody AulaDTO aulaDTO) {
        return cursoService.adicionarAula(moduloId, aulaDTO);
    }
}

