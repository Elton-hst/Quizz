package br.com.mjv.quizz.domain.question;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.persistence.entity.QuestionEntity;

import java.util.List;
import java.util.stream.Collectors;

public record Question(
        String text,
        String answer,
        Double score,
        List<Alternative> alternative) {

    public QuestionEntity toEntity() {
        return QuestionEntity.builder()
                .text(text)
                .answer(answer)
                .score(score)
                .alternative(alternative.stream().map(Alternative::toEntity).collect(Collectors.toList()))
                .build();
    }

}
