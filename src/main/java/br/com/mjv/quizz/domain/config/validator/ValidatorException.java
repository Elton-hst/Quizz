package br.com.mjv.quizz.domain.config.validator;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String message) {
        super(message);
    }
}