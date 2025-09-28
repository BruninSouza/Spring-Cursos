package servicoalunos.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import servicoalunos.dto.RegisterDTO;
import servicoalunos.service.AlunoService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AlunoService alunoService;

    public AuthController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        alunoService.registerUser(registerDTO);
        return ResponseEntity.ok().build();
    }
}
