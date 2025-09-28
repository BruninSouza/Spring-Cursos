package servicoinscricoes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicoinscricoes.dto.InscricaoDetalhesDTO;
import servicoinscricoes.dto.InscricaoDTO;
import servicoinscricoes.dto.InscricaoStatusUpdateDTO;
import servicoinscricoes.service.InscricaoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ping")
    public String ping() {
        return "Serviço de Inscrições a responder da porta: " + serverPort;
    }

    @PostMapping
    public InscricaoDTO criarInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) {
        return inscricaoService.createInscricao(inscricaoDTO);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<InscricaoDetalhesDTO> buscarInscricoesPorAluno(@PathVariable UUID alunoId) {
        return inscricaoService.getInscricoesByAlunoIdComDetalhes(alunoId);
    }

    @GetMapping("/{id}")
    public InscricaoDTO buscarInscricaoPorId(@PathVariable UUID id) {
        return inscricaoService.getInscricaoById(id);
    }

    @PutMapping("/{id}/status")
    public InscricaoDTO atualizarStatusInscricao(@PathVariable UUID id, @Valid @RequestBody InscricaoStatusUpdateDTO statusUpdateDTO) {
        return inscricaoService.updateStatus(id, statusUpdateDTO.status());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarInscricao(@PathVariable UUID id) {
        inscricaoService.deleteInscricao(id);
        return ResponseEntity.noContent().build();
    }
}
