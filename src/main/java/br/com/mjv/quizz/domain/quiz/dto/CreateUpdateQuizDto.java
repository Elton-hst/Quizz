package br.com.mjv.quizz.domain.quiz.dto;

import br.com.mjv.quizz.domain.question.dto.CreateUpdateQuestionDto;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;

import java.util.List;
import java.util.stream.Collectors;

public record CreateUpdateQuizDto(
        List<CreateUpdateQuestionDto> questionDto,
        String title,
        String description,
        String category,
        QuizDifficulty difficulty) {

}
