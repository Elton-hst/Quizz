package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessageRequest {
    private String messaging_product;
    private String recipient_type;
    private String to;
    private String type;
    private Template template;
}