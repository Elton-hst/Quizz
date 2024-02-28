package br.com.mjv.quizz.application.useCases.quiz;

import br.com.mjv.quizz.application.facede.QuizFacade;
import br.com.mjv.quizz.application.useCases.user.UserManagementUseCase;
import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
public class QuizManagementUseCase {

    private final QuizFacade quizService;
    private final UserManagementUseCase userManagementUseCase;

    public QuizManagementUseCase(QuizFacade quizFacade, UserManagementUseCase userManagementUseCase) {
        this.quizService = quizFacade;
        this.userManagementUseCase = userManagementUseCase;
    }

    private Quiz quiz;
    private String category;
    private Question currentQuestion;
    private int currentQuestionIndex;
    private Double userScore;

    public String startGame(User user, Quiz quiz) {
        log.info("Start game");

        int contQuestionIndex = 0;
        userScore = 0.0;

        userManagementUseCase.updateUser(user.phone(), ChatContext.RECEIVE_ANSWER);

        currentQuestion = quiz.question().get(contQuestionIndex);

        return formatQuestion(currentQuestion, contQuestionIndex + 1);
    }


    public String formatQuestion(Question question, int questionNumber) {
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

    public String response(User user, String text) {
        log.info("Response");

        if (text.equalsIgnoreCase(currentQuestion.answer())) {

            userScore += currentQuestion.score();
            currentQuestionIndex++;

            if (currentQuestionIndex < quiz.question().size()) {

                currentQuestion = quiz.question().get(currentQuestionIndex);
                return formatQuestion(currentQuestion, currentQuestionIndex + 1);

            } else {

                userManagementUseCase.updateUser(user.phone(), userScore);
                return "Parabéns! Você terminou o jogo. Seu score: " + userScore + finished(user.phone());

            }
        } else {
            return "Resposta incorreta. Tente novamente.";
        }
    }

    public Quiz findQuiz(String category, QuizDifficulty difficulty) {
        GetQuizDto quizDto = new GetQuizDto(category, difficulty);
        this.quiz = quizService.findQuiz(quizDto);
        return quiz;
    }

    public String startOrCreateGame(User user, String text) {
        log.info("Start or create game");

        if (text.equalsIgnoreCase("1")) {

            userManagementUseCase.updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);
            return "Digite a categoria que deseja jogar";

        } else if (text.equalsIgnoreCase("2")) {

            userManagementUseCase.updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);
            return "Digite a categoria que deseja criar";

        } else {
            return "Digite apenas os numeros 1 ou 2";
        }
    }

    public String responseCategory(User user, String text) {
        log.info("Response category");

        userManagementUseCase.updateUser(user.phone(), ChatContext.RESPONSE_DIFFICULTY);
        category = text;

        String message = "Qual dificuldade deseja jogar: \n\n" +
                "1 - Fácil \n" +
                "2 - Médio \n" +
                "3 - Difícil \n";

        return message;
    }

    public String responseDifficulty(User user, String text) {
        log.info("Response difficulty");

        if (text.equalsIgnoreCase("1")) {

            Quiz resultQuiz = findQuiz(category, QuizDifficulty.EASY);

            if (Objects.isNull(resultQuiz)) {
                userManagementUseCase.updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                userManagementUseCase.updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else if (text.equalsIgnoreCase("2")) {

            Quiz resultQuiz = findQuiz(category, QuizDifficulty.MEDIUM);

            if (Objects.isNull(resultQuiz)) {
                userManagementUseCase.updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                userManagementUseCase.updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else if (text.equalsIgnoreCase("3")) {
            Quiz resultQuiz = findQuiz(category, QuizDifficulty.EASY);

            if (Objects.isNull(resultQuiz)) {
                userManagementUseCase.updateUser(user.phone(), ChatContext.CREATE_GAME);
                return createGame(user);
            } else {
                userManagementUseCase.updateUser(user.phone(), ChatContext.START_GAME);
                return startGame(user, resultQuiz);
            }

        } else {
            return "Digite apenas os numeros 1, 2 ou 3";
        }
    }

    public String createGame(User user) {
        log.info("Create Game");

        this.quiz = null;
        this.category = null;
        this.currentQuestion = null;
        this.currentQuestionIndex = 0;
        this.userScore = 0.0;

        userManagementUseCase.updateUser(user.phone(), ChatContext.RESPONSE_CATEGORY);

        String message = "Para criar um Quizz precisamos de algumas informações:\n" +
                " - Pergunta\n" +
                " - Alternativas\n" +
                " - Qual a alternativa correta\n\n" +
                "Digite a categoria do Quizz que deseja criar";

        return message;
    }

    private String finished(String phone) {
        log.info("Finished");
        userManagementUseCase.updateUser(phone, ChatContext.INITIALIZED_CHAT);
        return "\nObrigado por jorgar!";
    }

}
