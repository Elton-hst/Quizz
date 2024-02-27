package br.com.mjv.quizz.infrastructure.persistence.entity;

import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity @Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity extends BasicEntity {

    private String text;
    private String answer;
    private Double score;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AlternativeEntity> alternative;

    public Question toQuestion() {
        return new Question(
                text,
                answer,
                score,
                alternative.stream().map(AlternativeEntity::toAlternative)
                        .collect(Collectors.toList())
        );
    }
}
