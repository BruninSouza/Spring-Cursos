package servicocursos.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import servicocursos.exception.ResourceNotFoundException;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ProblemDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ProblemDetails problem = new ProblemDetails(
            URI.create("https://seusite.com/errors/recurso-nao-encontrado"),
            "Recurso não encontrado",
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            URI.create(request.getDescription(false)),
            Instant.now()
    );
    return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String detail = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ProblemDetails problem = new ProblemDetails(
            URI.create("https://seusite.com/errors/dados-invalidos"),
            "Dados inválidos",
            status.value(),
            "A requisição contém um ou mais campos inválidos: " + detail,
            URI.create(request.getDescription(false)),
            Instant.now()
    );
    return new ResponseEntity<>(problem, headers, status);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetails> handleGenericException(Exception ex, WebRequest request) {
    ProblemDetails problem = new ProblemDetails(
            URI.create("https://seusite.com/errors/erro-interno"),
            "Erro interno do servidor",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Ocorreu um erro inesperado. Tente novamente mais tarde.",
            URI.create(request.getDescription(false)),
            Instant.now()
    );
    return new ResponseEntity<>(problem, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
