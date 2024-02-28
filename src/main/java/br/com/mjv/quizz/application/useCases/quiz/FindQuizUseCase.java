package br.com.mjv.quizz.application.useCases.quiz;

import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.QuizException;
import br.com.mjv.quizz.infrastructure.persistence.dao.QuizEntityDao;
import br.com.mjv.quizz.infrastructure.persistence.entity.QuizEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindQuizUseCase {

    private final QuizRepository repository;

    public FindQuizUseCase(QuizRepository repository) {
        this.repository = repository;
    }

    public Quiz execute(GetQuizDto quizDto) {
        Quiz quiz = new Quiz(quizDto.category(), quizDto.difficulty());
        return findByCategoryAndDifficulty(quiz).orElse(null);
    }

    private Optional<Quiz> findByCategoryAndDifficulty(Quiz quiz) {
        return repository.findByCategoryAndDifficulty(quiz);
    }

}
