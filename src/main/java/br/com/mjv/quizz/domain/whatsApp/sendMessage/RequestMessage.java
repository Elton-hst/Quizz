package br.com.mjv.quizz.domain.whatsApp.sendMessage;

public record RequestMessage(
        String messaging_product,
        String to,
        RequestMessageText text) {
}
