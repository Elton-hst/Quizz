package br.com.mjv.quizz.domain.config.result;

public class ReturnWithMessage<T> {
    private final String message;
    private final T data;

    public ReturnWithMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
