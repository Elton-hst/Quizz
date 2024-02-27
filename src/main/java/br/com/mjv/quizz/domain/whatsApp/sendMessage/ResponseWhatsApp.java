package br.com.mjv.quizz.domain.whatsApp.sendMessage;

import java.util.List;

public record ResponseWhatsApp(
        String messaging_product,
        List<ResponseWhatsappContact> contacts,
        List<ResponseWhatsappMessage> messages) {


}
