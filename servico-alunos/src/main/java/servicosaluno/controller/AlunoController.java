package  servicosaluno.controller;

import jakarta.validation.Valid;
import  servicosaluno.dto.AlunoDTO;
import  servicosaluno.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Alunos a responder da porta: " + serverPort;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO novoAluno = alunoService.createAluno(alunoDTO);
        // Retorna status 201 Created com a localização do novo recurso
        return ResponseEntity.created(URI.create("/api/alunos/" + novoAluno.id())).body(novoAluno);
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarAlunos() {
        return ResponseEntity.ok(alunoService.getAllAlunos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(alunoService.getAlunoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable UUID id, @Valid @RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.ok(alunoService.updateAluno(id, alunoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable UUID id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }
}