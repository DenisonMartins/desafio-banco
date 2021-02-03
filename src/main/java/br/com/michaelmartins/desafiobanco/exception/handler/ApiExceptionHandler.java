package br.com.michaelmartins.desafiobanco.exception.handler;

import br.com.michaelmartins.desafiobanco.exception.ApiExceptionDetails;
import br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String fieldsMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiExceptionDetails error = ApiExceptionDetails.ApiExceptionDetailsBuilder.newBuilder()
                .title("Check field(s)")
                .description(fieldsMessage)
                .status(status.value())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(LimiteMaximoTransferenciaException.class)
    public ResponseEntity<ApiExceptionDetails> handleLimiteMaximoTransferenciaException(
            LimiteMaximoTransferenciaException exception) {
        ApiExceptionDetails error = ApiExceptionDetails.ApiExceptionDetailsBuilder.newBuilder()
                .title("Limite não permitido")
                .description(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(error);
    }
}
