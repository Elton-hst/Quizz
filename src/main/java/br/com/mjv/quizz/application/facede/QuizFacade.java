package br.com.mjv.quizz.application.facede;

import br.com.mjv.quizz.application.useCases.quiz.CreateQuizUseCase;
import br.com.mjv.quizz.application.useCases.quiz.FindQuizUseCase;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizFacade {

    private final CreateQuizUseCase createQuizUseCase;
    private final FindQuizUseCase findQuizUseCase;

    public Quiz createQuiz(CreateUpdateQuizDto quizDto) {
        return createQuizUseCase.execute(quizDto);
    }

    public Quiz findQuiz(GetQuizDto quizDto) {
        return findQuizUseCase.execute(quizDto);
    }
}
