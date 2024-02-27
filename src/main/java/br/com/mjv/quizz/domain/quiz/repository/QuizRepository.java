package br.com.mjv.quizz.domain.quiz.repository;

import br.com.mjv.quizz.domain.config.repository.Repository;
import br.com.mjv.quizz.domain.quiz.Quiz;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends Repository<Quiz, UUID> {
    Optional<Quiz> findByCategoryAndDifficulty(Quiz quiz);
}
