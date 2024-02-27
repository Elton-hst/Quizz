package br.com.mjv.quizz.infrastructure.configs.exception;

public class GameException extends RuntimeException{
    public GameException(String message) {
        super(message);
    }
}
