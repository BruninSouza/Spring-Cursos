package servicoinscricoes.controller;

import servicoinscricoes.entity.Inscricao;
import servicoinscricoes.repository.InscricaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Inscrições respondendo da porta: " + serverPort;
    }

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @PostMapping
    public Inscricao criarInscricao(@RequestBody Inscricao inscricao) {
        return inscricaoRepository.save(inscricao);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Inscricao>> buscarInscricoesPorAluno(@PathVariable UUID alunoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoId(alunoId);
        return ResponseEntity.ok(inscricoes);
    }
}
