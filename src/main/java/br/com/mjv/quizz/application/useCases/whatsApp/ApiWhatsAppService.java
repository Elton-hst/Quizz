package br.com.mjv.quizz.application.useCases.whatsApp;

import br.com.mjv.quizz.domain.whatsApp.sendMessage.RequestMessage;
import br.com.mjv.quizz.domain.whatsApp.sendMessage.RequestMessageText;
import br.com.mjv.quizz.infrastructure.web.dto.MessageDto;
import br.com.mjv.quizz.domain.whatsApp.sendMessage.ResponseWhatsApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

public class ApiWhatsAppService {

    private final RestClient restClient;

    public ApiWhatsAppService(@Value("${whatsapp.identification}") String identification, @Value("${whatsapp.token}") String token) {
        restClient = RestClient.builder()
                .baseUrl("https://graph.facebook.com/v18.0/" + identification + "/messages")
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public ResponseWhatsApp sendMessage(MessageDto messageDto) {
        try {
            RequestMessage request = new RequestMessage("whatsapp", messageDto.number(), new RequestMessageText(messageDto.message()));

            String response = restClient.post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(String.class);

            ObjectMapper obj = new ObjectMapper();
            return obj.readValue(response, ResponseWhatsApp.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

}
