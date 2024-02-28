package br.com.mjv.quizz.application.useCases.whatsApp;

import br.com.mjv.quizz.application.facede.UserFacade;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.dto.GetUserDto;
import br.com.mjv.quizz.infrastructure.web.dto.InfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Slf4j
public class ReadMessageUseCase {

    private final ContextChatUseCase contextChat;
    private final UserFacade userService;

    public ReadMessageUseCase(ContextChatUseCase contextChat, UserFacade userService) {
        this.contextChat = contextChat;
        this.userService = userService;
    }

    public void execute(InfoDto dto) {

        GetUserDto userDto = new GetUserDto(dto.getPhone());
        User user = userService.findUser(userDto);

        if (Objects.isNull(user)) {

            CreateUpdateUserDto createUserDto = new CreateUpdateUserDto(dto.getName(), dto.getPhone());
            var result = userService.createUser(createUserDto, Locale.ENGLISH);

            User user1 = result.getData();
            log.info("Status: {}", result.getMessage());
            log.info("User -> name: {}, phone: {}, context: {}", user1.name(), user1.phone(), user1.context());

            contextChat.execute(user1, null);

        } else {
            log.info("User -> name: {}, phone: {}, context: {}", user.name(), user.phone(), user.context());
            contextChat.execute(user, dto.getText());
        }

    }

}
