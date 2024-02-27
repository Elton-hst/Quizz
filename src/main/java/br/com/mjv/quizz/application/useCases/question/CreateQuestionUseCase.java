package br.com.mjv.quizz.application.useCases.question;

import br.com.mjv.quizz.application.useCases.alternative.CreateAlternativeUseCase;
import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.question.dto.CreateUpdateQuestionDto;
import br.com.mjv.quizz.domain.question.repository.QuestionsRepository;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.configs.exception.QuestionException;
import br.com.mjv.quizz.infrastructure.configs.exception.QuizException;
import br.com.mjv.quizz.infrastructure.web.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateQuestionUseCase {

    private final CreateAlternativeUseCase createAlternativeUseCase;

    public List<Question> execute(List<CreateUpdateQuestionDto> questionDtos) {
        List<Question> questions = new ArrayList<>();
        for (CreateUpdateQuestionDto questionDto : questionDtos) {
            List<Alternative> alternative = createAlternativeUseCase.execute(questionDto.alternativeDtos());

            Question question = new Question(
                    questionDto.text(),
                    questionDto.answer(),
                    questionDto.score(),
                    alternative
            );
            questions.add(question);
        }
        return questions;
    }

}
