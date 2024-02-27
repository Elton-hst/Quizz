package br.com.mjv.quizz.infrastructure.persistence.repository;

import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.infrastructure.persistence.dao.QuizEntityDao;
import br.com.mjv.quizz.infrastructure.persistence.entity.QuizEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {

    private final QuizEntityDao dao;

    @Override
    public Optional<Quiz> add(Quiz quiz) {
        var created = dao.save(quiz.toEntity());
        return Optional.of(created.toQuiz());
    }

    @Override
    public Optional<Quiz> update(Quiz quiz) {
        return Optional.empty();
    }

    @Override
    public Optional<Quiz> findByCategoryAndDifficulty(Quiz quiz) {
        return dao.findByCategoryAndDifficulty(quiz.category(), quiz.difficulty()).map(QuizEntity::toQuiz);
    }

    @Override
    public Optional<Quiz> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Quiz> findAll() {
        return null;
    }

    @Override
    public Optional<Quiz> deleteById(UUID id) {
        return Optional.empty();
    }

}
