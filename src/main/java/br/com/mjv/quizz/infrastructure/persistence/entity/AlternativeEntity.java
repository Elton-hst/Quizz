package br.com.mjv.quizz.infrastructure.persistence.entity;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity @Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeEntity extends BasicEntity{

    private String text;
    private boolean isCorrect;
    private String referenceLetter;

    public Alternative toAlternative(){
        return new Alternative(
                text,
                isCorrect,
                referenceLetter
        );
    }

}
