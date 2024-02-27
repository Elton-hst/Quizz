package br.com.mjv.quizz.domain.quiz.dto;

import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;

public record GetQuizDto(
        String category,
        QuizDifficulty difficulty) {



}
