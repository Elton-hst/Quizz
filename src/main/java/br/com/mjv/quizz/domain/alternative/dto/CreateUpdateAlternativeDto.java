package br.com.mjv.quizz.domain.alternative.dto;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.question.dto.CreateUpdateQuestionDto;

public record CreateUpdateAlternativeDto(
        String text,
        boolean isCorrect,
        String referenceLetter) {

    public Alternative toAlternative(){
        return new Alternative(
                text,
                isCorrect,
                referenceLetter
        );
    }

}
