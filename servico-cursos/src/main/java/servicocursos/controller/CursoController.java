package servicocursos.controller;

import jakarta.validation.Valid;
import servicocursos.dto.CursoDTO;
import servicocursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Cursos a responder da porta: " + serverPort;
    }

    @PostMapping
    public ResponseEntity<CursoDTO> criarCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO novoCurso = cursoService.createCurso(cursoDTO);
        return ResponseEntity.created(URI.create("/api/cursos/" + novoCurso.id())).body(novoCurso);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarCursoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cursoService.getCursoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> atualizarCurso(@PathVariable UUID id, @Valid @RequestBody CursoDTO cursoDTO) {
        return ResponseEntity.ok(cursoService.updateCurso(id, cursoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable UUID id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
