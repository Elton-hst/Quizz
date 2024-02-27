package br.com.mjv.quizz.infrastructure.web;

import br.com.mjv.quizz.application.useCases.whatsApp.ApiWhatsAppService;
import br.com.mjv.quizz.application.useCases.whatsApp.ReadMessageUseCase;
import br.com.mjv.quizz.domain.whatsApp.webhook.*;
import br.com.mjv.quizz.infrastructure.web.dto.InfoDto;
import br.com.mjv.quizz.infrastructure.web.dto.MessageDto;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@RestController
@RequestMapping({"/whatsapp"})
@RequiredArgsConstructor
public class WhatsAppController {

    private final ApiWhatsAppService apiWhatsApp;
    private final ReadMessageUseCase readMessageUseCase;
    private final ResourceBundle messageBundle;

    private String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(messageBundle.getBaseBundleName(), locale).getString(key);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ReturnWithMessage> sendMessage(@RequestBody MessageDto messageDto, final Locale locale) throws JsonProcessingException {
        var response = apiWhatsApp.sendMessage(messageDto);
        String message = getMessage("api.sendMessage.success", locale);
        return ResponseEntity.ok(new ReturnWithMessage(message, response));
    }

    @PostMapping({"/webhooks"})
    @Transactional
    public ResponseEntity<String> replyWhatsAppMessage(@RequestBody WhatsAppMessageReplyRequest request) {
        if (Objects.isNull(request.getEntry().getFirst().getChanges().getFirst().getValue().getMessages())) {

            return new ResponseEntity<>("Message is empty", HttpStatus.BAD_REQUEST);

        } else {
            var name = request.getEntry().getFirst().getChanges().getFirst().
                    getValue().getContacts().getFirst().getProfile().getName();

            var number = request.getEntry().getFirst().getChanges().getFirst().
                    getValue().getMessages().getFirst().getFrom();

            var message = request.getEntry().getFirst().getChanges().getFirst().
                    getValue().getMessages().getFirst().getText().getBody();

            log.info("Name: {}", name);
            log.info("Number: {}", number);
            log.info("Message: {}", message);

            InfoDto infoDto = new InfoDto();
            infoDto.setName(name);
            infoDto.setText(message);
            infoDto.setPhone("5581986638021");

            readMessageUseCase.execute(infoDto);
        }
        return new ResponseEntity<>("Success receiver message", HttpStatus.OK);
    }

}
