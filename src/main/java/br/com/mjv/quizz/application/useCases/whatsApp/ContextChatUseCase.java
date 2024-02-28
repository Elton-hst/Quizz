package br.com.mjv.quizz.application.useCases.whatsApp;

import br.com.mjv.quizz.application.useCases.quiz.QuizManagementUseCase;
import br.com.mjv.quizz.application.useCases.user.UserManagementUseCase;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.infrastructure.web.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class ContextChatUseCase {

    private final UserManagementUseCase userManagementUseCase;
    private final QuizManagementUseCase quizManagementUseCase;
    private final MessageManagementUseCase messageManagementUseCase;

    public ContextChatUseCase(UserManagementUseCase userManagementUseCase, QuizManagementUseCase quizManagementUseCase, MessageManagementUseCase messageManagementUseCase) {
        this.userManagementUseCase = userManagementUseCase;
        this.quizManagementUseCase = quizManagementUseCase;
        this.messageManagementUseCase = messageManagementUseCase;
    }

    public void execute(User user, String text) {
        String context = switch (user.context()) {
            case INITIALIZED_CHAT -> userManagementUseCase.initializedChat(user);
            case START_OR_CREATE -> quizManagementUseCase.startOrCreateGame(user, text);
            case RESPONSE_CATEGORY -> quizManagementUseCase.responseCategory(user, text);
            case RESPONSE_DIFFICULTY -> quizManagementUseCase.responseDifficulty(user, text);
            case RECEIVE_ANSWER -> quizManagementUseCase.response(user, text);
            case CREATE_GAME, START_GAME, GAME_FINISHED -> null;
        };

        MessageDto messageDto = new MessageDto(user.phone(), context);
        messageManagementUseCase.sendMessage(messageDto);
    }

}
