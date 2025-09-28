package servicoalunos.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AlunoDTO criarAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        return alunoService.createAluno(alunoDTO);
    }

    @GetMapping
    public List<AlunoDTO> listarAlunos() {
        return alunoService.getAllAlunos();
    }

    @GetMapping("/{id}")
    public AlunoDTO buscarAlunoPorId(@PathVariable UUID id) {
        return alunoService.getAlunoById(id);
    }

    @PutMapping("/{id}")
    public AlunoDTO atualizarAluno(@PathVariable UUID id, @Valid @RequestBody AlunoDTO alunoDTO) {
        return alunoService.updateAluno(id, alunoDTO);
    }

    @GetMapping("/{id}/detalhes")
    public AlunoDetalhesDTO buscarAlunoDetalhesPorId(@PathVariable UUID id) {
        return alunoService.getAlunoDetalhesById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAluno(@PathVariable UUID id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }
}