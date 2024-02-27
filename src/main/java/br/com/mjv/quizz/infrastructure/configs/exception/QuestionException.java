package br.com.mjv.quizz.infrastructure.configs.exception;

public class QuestionException extends RuntimeException{
    public QuestionException(String message) {
        super(message);
    }
}
