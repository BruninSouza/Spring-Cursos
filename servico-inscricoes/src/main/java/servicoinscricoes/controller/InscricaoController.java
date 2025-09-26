package servicoinscricoes.controller;

import jakarta.validation.Valid;
import servicoinscricoes.dto.InscricaoDTO;
import servicoinscricoes.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<InscricaoDTO> criarInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) {
        InscricaoDTO novaInscricao = inscricaoService.createInscricao(inscricaoDTO);
        return ResponseEntity.created(URI.create("/api/inscricoes/" + novaInscricao.id())).body(novaInscricao);
    }
}
