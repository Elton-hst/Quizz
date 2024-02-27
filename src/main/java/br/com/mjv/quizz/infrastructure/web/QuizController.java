package br.com.mjv.quizz.infrastructure.web;

import br.com.mjv.quizz.application.facede.QuizFacade;
import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.domain.quiz.dto.GetQuizDto;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping({"/quiz"})
@RequiredArgsConstructor
public class QuizController {

    private final QuizFacade quizFacade;

    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody CreateUpdateQuizDto quizDto) {
        var quiz = quizFacade.createQuiz(quizDto);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping({"/{category}/{difficulty}"})
    public ResponseEntity<?> findQuiz(@PathVariable String category,
                                      @PathVariable QuizDifficulty difficulty) {
        GetQuizDto quizDto = new GetQuizDto(category, difficulty);
        var quiz = quizFacade.findQuiz(quizDto);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

}
