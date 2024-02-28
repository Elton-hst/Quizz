package br.com.mjv.quizz.application.useCases.whatsApp;

import br.com.mjv.quizz.infrastructure.web.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public class MessageManagementUseCase {

    private final ApiWhatsAppService apiWhatsAppService;

    public MessageManagementUseCase(ApiWhatsAppService apiWhatsAppService) {
        this.apiWhatsAppService = apiWhatsAppService;
    }

    public void sendMessage(MessageDto messageDto) {
        ResponseEntity.ok(apiWhatsAppService.sendMessage(messageDto));
    }
}
