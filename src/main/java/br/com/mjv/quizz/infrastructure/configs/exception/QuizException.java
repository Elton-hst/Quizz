package br.com.mjv.quizz.infrastructure.configs.exception;

public class QuizException extends RuntimeException {
    public QuizException(String message) {
        super(message);
    }
}
