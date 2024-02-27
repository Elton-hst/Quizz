package br.com.mjv.quizz.application.useCases.whatsApp;

import br.com.mjv.quizz.application.facede.QuizFacade;
import br.com.mjv.quizz.application.useCases.quiz.FindQuizUseCase;
import br.com.mjv.quizz.application.useCases.user.UpdateUserUseCase;
import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;
import br.com.mjv.quizz.infrastructure.web.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service @Slf4j
@RequiredArgsConstructor
public class ContextChatUseCase {

    private final UpdateUserUseCase updateUser;
    private final ApiWhatsAppService apiWhatsAppService;
    private final QuizFacade quizService;

    private Quiz quiz;
    private String category;
    private Question currentQuestion;
    private int currentQuestionIndex;
    private Double userScore;

    public void execute(User user, String text) throws JsonProcessingException {
        String context = switch (user.context()) {
            case INITIALIZED_CHAT -> initializedChat(user);
            case START_OR_CREATE -> startOrCreateGame(user, text);
            case RESPONSE_CATEGORY -> responseCategory(user, text);
            case RESPONSE_DIFFICULTY -> responseDifficulty(user, text);
            case RECEIVE_ANSWER -> response(user, text);
            case CREATE_GAME, START_GAME, GAME_FINISHED -> null;
        };

        MessageDto messageDto = new MessageDto(user.phone(), context);
        ResponseEntity.ok(apiWhatsAppService.sendMessage(messageDto));
    }

    private User updateUser(String phone, ChatContext context) {
        CreateUpdateUserDto userDto = new CreateUpdateUserDto(phone, context);
        var result = updateUser.execute(userDto, Locale.ENGLISH);

        log.info("Status: {}", result.getMessage());
        log.info("User -> name: {}, phone: {}, context: {}", result.getData().name(), result.getData().phone(), result.getData().context());

        return result.getData();
    }

    private User updateUser(String phone, Double score) {
        CreateUpdateUserDto userDto = new CreateUpdateUserDto(phone, score);
        var result = updateUser.execute(userDto, Locale.ENGLISH);

        log.info("Status: {}", result.getMessage());
        log.info("User -> name: {}, phone: {}, context: {}", result.getData().name(), result.getData().phone(), result.getData().context());

        return result.getData();
    }

    private Quiz findQuiz(String category, QuizDifficulty difficulty) {
        GetQuizDto quizDto = new GetQuizDto(category, difficulty);
        this.quiz = quizService.findQuiz(quizDto);
        return quiz;
    }

    private String initializedChat(User user) {
        log.info("Initialized chat");

        var result = updateUser(user.phone(), ChatContext.START_OR_CREATE);

        String message = String.format(
                "Olá, bem vindo(a) %s ao Quizz \n" +
                "\nO que deseja fazer: \n" +
                "1 - jogar uma partida \n" +
                "2 - criar uma partida",
                result.name());

        return message;
    }

    private String startOrCreateGame(User user, String text) {
        log.info("Start or create game");

        if (text.equalsIgnoreCase("1")) {

            updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);
            return "Digite a categoria que deseja jogar";

        } else if (text.equalsIgnoreCase("2")) {

            updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);
            return "Digite a categoria que deseja criar";

        } else {
            return "Digite apenas os numeros 1 ou 2";
        }
    }

    private String responseCategory(User user, String text) {
        log.info("Response category");

        updateUser(user.phone(), ChatContext.RESPONSE_DIFFICULTY);
        category = text;

        String message = "Qual dificuldade deseja jogar: \n\n" +
                "1 - Fácil \n" +
                "2 - Médio \n" +
                "3 - Difícil \n";

        return message;
    }

    private String responseDifficulty(User user, String text) {
        log.info("Response difficulty");

        if (text.equalsIgnoreCase("1")) {

            Quiz resultQuiz = findQuiz(category, QuizDifficulty.EASY);

            if (Objects.isNull(resultQuiz)) {
                updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else if (text.equalsIgnoreCase("2")) {

            Quiz resultQuiz = findQuiz(category, QuizDifficulty.MEDIUM);

            if (Objects.isNull(resultQuiz)) {
                updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else if (text.equalsIgnoreCase("3")) {
            Quiz resultQuiz = findQuiz(category, QuizDifficulty.EASY);

            if (Objects.isNull(resultQuiz)) {
                updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else {
            return "Digite apenas os numeros 1, 2 ou 3";
        }
    }

    private String startGame(User user, Quiz quiz) {
        log.info("Start game");

        int contQuestionIndex = 0;
        userScore = 0.0;

        updateUser(user.phone(), ChatContext.RECEIVE_ANSWER);

        currentQuestion = quiz.question().get(contQuestionIndex);

        return formatQuestion(currentQuestion, contQuestionIndex + 1);
    }


    private String formatQuestion(Question question, int questionNumber) {
        StringBuilder questionBuilder = new StringBuilder();
        questionBuilder.append(String.format(
                "%dº): %s?\n",
                questionNumber,
                question.text()
        ));

        int contAlternative = 1;
        for (Alternative alternative : question.alternative()) {
            String alternativeFormat = String.format(
                    "\n%d) %s\n",
                    contAlternative,
                    alternative.text()
            );
            questionBuilder.append(alternativeFormat);
            contAlternative++;
        }
        return questionBuilder.toString();
    }

    private String response(User user, String text) {
        log.info("Response");

        if (text.equalsIgnoreCase(currentQuestion.answer())) {

            userScore += currentQuestion.score();
            currentQuestionIndex++;

            if (currentQuestionIndex < quiz.question().size()) {

                currentQuestion = quiz.question().get(currentQuestionIndex);
                return formatQuestion(currentQuestion, currentQuestionIndex + 1);

            } else {

                updateUser(user.phone(), userScore);
                return "Parabéns! Você terminou o jogo. Seu score: " + userScore + finished(user.phone());

            }
        } else {
            return "Resposta incorreta. Tente novamente.";
        }
    }

    private String createGame(User user) {
        log.info("Create Game");

        this.quiz = null;
        this.category = null;
        this.currentQuestion = null;
        this.currentQuestionIndex = 0;
        this.userScore = 0.0;

        updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);

        String message = "Para criar um Quizz precisamos de algumas informações:\n" +
                " - Pergunta\n" +
                " - Alternativas\n" +
                " - Qual a alternativa correta\n\n" +
                "Digite a categoria do Quizz que deseja criar";

        return message;
    }

    private String finished(String phone) {
        log.info("Finished");
        updateUser(phone, ChatContext.INITIALIZED_CHAT);
        return "\nObrigado por jorgar!";
    }

}
