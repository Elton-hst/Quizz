package br.com.mjv.quizz.application.useCases.quiz;

import br.com.mjv.quizz.application.useCases.question.CreateQuestionUseCase;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.QuizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public class CreateQuizUseCase {

    private final QuizRepository repository;
    private final CreateQuestionUseCase createQuestionUseCase;

    public CreateQuizUseCase(QuizRepository quizRepository, CreateQuestionUseCase createQuestionUseCase) {
        this.repository = quizRepository;
        this.createQuestionUseCase = createQuestionUseCase;
    }

    public Quiz execute(CreateUpdateQuizDto quizDto) {
        var questions = createQuestionUseCase.execute(quizDto.questionDto());

        Quiz quiz = new Quiz(
                questions,
                quizDto.title(),
                quizDto.description(),
                quizDto.category(),
                quizDto.difficulty()
        );

        return add(quiz);
    }

    private Quiz add(Quiz quiz) {
        return repository.add(quiz)
                .orElseThrow(() -> new QuizException("Erro ao criar um quiz"));
    }

}
