package br.com.mjv.quizz.domain.quiz;

import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.infrastructure.persistence.entity.QuizEntity;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;

import java.util.List;
import java.util.stream.Collectors;

public record Quiz(
        List<Question> question,
        String title,
        String description,
        String category,
        QuizDifficulty difficulty) {

    public Quiz(List<Question> question, String title, String description, String category, QuizDifficulty difficulty) {
        this.question = question;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Quiz(String category, QuizDifficulty difficulty) {
        this(null, null, null, category, difficulty);
    }

    public QuizEntity toEntity() {
        return QuizEntity.builder()
                .question(question.stream().map(Question::toEntity).collect(Collectors.toList()))
                .title(title)
                .description(description)
                .category(category)
                .difficulty(difficulty)
                .build();
    }

}
