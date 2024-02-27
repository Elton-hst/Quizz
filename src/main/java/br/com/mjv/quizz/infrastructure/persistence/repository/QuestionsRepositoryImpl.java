package br.com.mjv.quizz.infrastructure.persistence.repository;

import br.com.mjv.quizz.domain.question.Question;
import br.com.mjv.quizz.domain.question.repository.QuestionsRepository;
import br.com.mjv.quizz.infrastructure.persistence.dao.QuestionEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QuestionsRepositoryImpl implements QuestionsRepository {

    private final QuestionEntityDao dao;

    @Override
    public Optional<Question> add(Question question) {
        var created = dao.save(question.toEntity());
        return Optional.of(created.toQuestion());
    }

    @Override
    public Optional<Question> update(Question question) {
        return Optional.empty();
    }

    @Override
    public Optional<Question> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public Optional<Question> deleteById(UUID id) {
        return Optional.empty();
    }
}
