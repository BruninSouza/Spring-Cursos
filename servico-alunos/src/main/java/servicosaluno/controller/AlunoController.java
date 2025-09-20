package servicosaluno.controller;

import servicosaluno.entity.Aluno;
import servicosaluno.repository.AlunoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Alunos a responder da porta: " + serverPort;
    }

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @GetMapping
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }
}