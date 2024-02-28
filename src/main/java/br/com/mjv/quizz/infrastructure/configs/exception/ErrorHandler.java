package br.com.mjv.quizz.infrastructure.configs.exception;

import br.com.mjv.quizz.domain.config.validator.ValidatorException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException exception) {
        return ResponseEntity.badRequest().body("Error: " + exception.getLocalizedMessage());
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<?> validatorException(ValidatorException exception) {
        return ResponseEntity.badRequest().body("Error: " + exception.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleError404() {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<?> handleError500(JpaSystemException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }

    private record ValidationErrorData(String field, List<String> message) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), new ArrayList<>(Collections.singletonList(error.getDefaultMessage())));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        List<ValidationErrorData> messages = new ArrayList<>(erros.size());

        erros.forEach(erro -> {
            if (messages.stream().anyMatch(dadosErroValidacao -> Objects.equals(dadosErroValidacao.field(), erro.getField()))) {
                ValidationErrorData dados = messages.stream().filter(dadosErroValidacao ->
                        Objects.equals(dadosErroValidacao.field(), erro.getField())
                ).findFirst().get();

                messages.remove(dados);
                List<String> mensagens = dados.message();
                String erroMessage = erro.getDefaultMessage();
                mensagens.add(erroMessage);
                messages.add(new ValidationErrorData(erro.getField(), mensagens));
            } else {
                messages.add(new ValidationErrorData(erro));
            }
        });
        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationError(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ValidationErrorData> messages = new ArrayList<>(constraintViolations.size());

        constraintViolations.forEach(constraintViolation -> {
            if (messages.stream().anyMatch(dadosErroValidacao -> Objects.equals(dadosErroValidacao.field(), constraintViolation.getPropertyPath().toString()))) {
                ValidationErrorData dados = messages.stream().filter(dadosErroValidacao ->
                        Objects.equals(dadosErroValidacao.field(), constraintViolation.getPropertyPath().toString())
                ).findFirst().get();
                messages.remove(dados);

                List<String> mensagens = dados.message();
                mensagens.add(constraintViolation.getMessage());
                messages.add(new ValidationErrorData(constraintViolation.getPropertyPath().toString(),
                        mensagens));
            }

            messages.add(new ValidationErrorData(constraintViolation.getPropertyPath().toString(),
                    Collections.singletonList(constraintViolation.getMessage())));
        });

        return ResponseEntity.badRequest().body(messages);
    }

}
