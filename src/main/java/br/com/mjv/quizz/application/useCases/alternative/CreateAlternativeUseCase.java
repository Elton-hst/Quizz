package br.com.mjv.quizz.application.useCases.alternative;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.alternative.dto.CreateUpdateAlternativeDto;
import br.com.mjv.quizz.domain.alternative.repository.AlternativesRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.AlternativeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class CreateAlternativeUseCase {

    public List<Alternative> execute(List<CreateUpdateAlternativeDto> alternativeDtos) {
        List<Alternative> alternatives = new ArrayList<>();
        for (CreateUpdateAlternativeDto alternativeDto : alternativeDtos) {
            Alternative alternative = new Alternative(
                    alternativeDto.text(),
                    alternativeDto.isCorrect(),
                    alternativeDto.referenceLetter()
            );
            alternatives.add(alternative);
        }
        return alternatives;
    }

}
