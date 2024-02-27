package br.com.mjv.quizz.infrastructure.configs.exception;

public class JaExisteUmUsuario extends RuntimeException{
    public JaExisteUmUsuario(String message) {
        super(message);
    }
}
