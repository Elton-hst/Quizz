package br.com.mjv.quizz.application.useCases.quiz;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.QuestionException;
import br.com.mjv.quizz.infrastructure.configs.exception.QuizException;
import br.com.mjv.quizz.infrastructure.persistence.dao.QuizEntityDao;
import br.com.mjv.quizz.infrastructure.persistence.entity.QuizEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
public class FindQuizUseCase {

    private final QuizRepository repository;
    private final ResourceBundle resourceBundle;

    public FindQuizUseCase(QuizRepository repository, ResourceBundle resourceBundle) {
        this.repository = repository;
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(resourceBundle.getBaseBundleName(), locale).getString(key);
    }

    public Quiz execute(GetQuizDto quizDto) {
        Quiz quiz = new Quiz(quizDto.category(), quizDto.difficulty());
        return result(quiz, Locale.ENGLISH);
    }

    public Quiz execute(GetQuizDto quizDto, Locale locale) {
        Quiz quiz = new Quiz(quizDto.category(), quizDto.difficulty());
        return result(quiz, locale);
    }

    private Quiz result(Quiz quiz, Locale locale) {
        var result = findByCategoryAndDifficulty(quiz);

        return result.ifSuccess(quiz1 -> {
            log.info("Quiz -> category: {}, difficulty: {}, status: {}", quiz.category(), quiz.difficulty(), getMessage("quiz.find.success", locale));
            return quiz1;
        }).throwsEarlyIf(RuntimeException.class, error -> {
            log.info("Quiz -> category: {}, difficulty: {}, status: {}", quiz.category(), quiz.difficulty(), getMessage("quiz.find.fail", locale));
            return new QuestionException("Error create quiz");
        }).execute();
    }


    private Result<Quiz, RuntimeException> findByCategoryAndDifficulty(Quiz quiz) {
        return repository.findByCategoryAndDifficulty(quiz)
                .map(Result::<Quiz, RuntimeException>successWithReturn)
                .orElse(Result.failWithProblem(new QuestionException("Error create quiz")));
    }

}
