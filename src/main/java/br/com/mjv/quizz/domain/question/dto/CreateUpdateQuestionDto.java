package br.com.mjv.quizz.domain.question.dto;

import br.com.mjv.quizz.domain.alternative.dto.CreateUpdateAlternativeDto;
import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;

import java.util.List;
import java.util.stream.Collectors;

public record CreateUpdateQuestionDto(
        String text,
        String answer,
        Double score,
        List<CreateUpdateAlternativeDto> alternativeDtos) {



}
