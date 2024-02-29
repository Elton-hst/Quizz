package br.com.mjv.quizz.application.useCases.quiz;

import br.com.mjv.quizz.application.useCases.question.CreateQuestionUseCase;
import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.QuestionException;
import br.com.mjv.quizz.infrastructure.configs.exception.QuizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class CreateQuizUseCase {

    private final QuizRepository repository;
    private final CreateQuestionUseCase createQuestionUseCase;
    private final ResourceBundle resourceBundle;

    public CreateQuizUseCase(QuizRepository quizRepository, CreateQuestionUseCase createQuestionUseCase, ResourceBundle resourceBundle) {
        this.repository = quizRepository;
        this.createQuestionUseCase = createQuestionUseCase;
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(resourceBundle.getBaseBundleName(), locale).getString(key);
    }

    public Quiz execute(CreateUpdateQuizDto quizDto) {
        var questions = createQuestionUseCase.execute(quizDto.questionDto());
        Quiz quiz = new Quiz(questions, quizDto.title(), quizDto.description(), quizDto.category(), quizDto.difficulty());
        return result(quiz, Locale.ENGLISH);
    }

    public Quiz execute(CreateUpdateQuizDto quizDto, Locale locale) {
        var questions = createQuestionUseCase.execute(quizDto.questionDto());
        Quiz quiz = new Quiz(questions, quizDto.title(), quizDto.description(), quizDto.category(), quizDto.difficulty());
        return result(quiz, locale);
    }

    private Quiz result(Quiz quiz, Locale locale) {
        var result = addUser(quiz);

        return result.ifSuccess(quiz1 -> {
            log.info("Quiz -> category: {}, status: {}", quiz1.category(), getMessage("quiz.create.success", locale));
            return quiz1;
        }).throwsEarlyIf(RuntimeException.class, error -> {
            log.info("Quiz -> category: {}, status: {}", quiz.category(), getMessage("quiz.create.fail", locale));
            return new RuntimeException("Error create quiz");
        }).execute();
    }


    private Result<Quiz, RuntimeException> addUser(Quiz quiz) {
        return repository.add(quiz)
                .map(Result::<Quiz, RuntimeException>successWithReturn)
                .orElse(Result.failWithProblem(new QuestionException("Error create quiz")));
    }

}
