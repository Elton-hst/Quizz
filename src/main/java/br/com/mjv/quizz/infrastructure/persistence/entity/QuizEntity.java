package br.com.mjv.quizz.infrastructure.persistence.entity;

import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.BasicEntity;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Entity @Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuizEntity extends BasicEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<QuestionEntity> question;
    private String title;
    private String description;
    private String category;
    private QuizDifficulty difficulty;

    public Quiz toQuiz() {
        return new Quiz(
                question.stream().map(QuestionEntity::toQuestion).collect(Collectors.toList()),
                title,
                description,
                category,
                difficulty
        );
    }
}
