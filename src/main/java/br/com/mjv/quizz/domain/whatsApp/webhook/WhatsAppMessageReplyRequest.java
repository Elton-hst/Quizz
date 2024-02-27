package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessageReplyRequest {
    private String object;
    private List<Entry> entry;
}