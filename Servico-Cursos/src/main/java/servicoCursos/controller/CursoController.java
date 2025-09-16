package servicocursos.controller;

import servicocursos.entity.Curso;
import servicocursos.repository.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Value("${server.port}")
    private String serverPort;

    // Endpoint para a demonstração de balanceamento de carga
    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Cursos respondendo da porta: " + serverPort;
    }

    @PostMapping
    public Curso criarCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> listarCursos() {
        System.out.println("LOG: Listando cursos na instância da porta " + serverPort);
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarCursoPorId(@PathVariable UUID id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}