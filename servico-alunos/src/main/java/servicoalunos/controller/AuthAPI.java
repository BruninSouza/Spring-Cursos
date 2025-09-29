package servicoalunos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.http.ResponseEntity;
import servicoalunos.dto.LoginDTO;
import servicoalunos.dto.RegisterDTO;
import servicoalunos.exception.ErroResponse;

@Tag(name = "Autenticação", description = "Endpoints para registo e login de utilizadores")
public interface AuthAPI {

    @Operation(summary = "Regista um novo utilizador", description = "Cria uma nova conta de aluno no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilizador registado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de registo inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponse.class)))
    })
    ResponseEntity<Void> register(RegisterDTO registerDTO);

    @Operation(summary = "Autentica um utilizador e retorna um token JWT",
            description = "Use as credenciais (email e senha) para obter um token de autenticação. O token será retornado no cabeçalho 'Authorization' da resposta.")
    @RequestBody(description = "Credenciais do utilizador", required = true,
            content = @Content(schema = @Schema(implementation = LoginDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido. O token JWT está no cabeçalho 'Authorization'."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponse.class)))
    })
    @PostMapping("/login")
    default void login(LoginDTO loginDTO) {
    }
}
