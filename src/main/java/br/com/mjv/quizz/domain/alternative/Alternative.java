package br.com.mjv.quizz.domain.alternative;

import br.com.mjv.quizz.infrastructure.persistence.entity.AlternativeEntity;

public record Alternative(
        String text,
        boolean isCorrect,
        String referenceLetter) {

    public AlternativeEntity toEntity(){
        return AlternativeEntity.builder()
                .text(text)
                .isCorrect(isCorrect)
                .referenceLetter(referenceLetter)
                .build();
    }

}
