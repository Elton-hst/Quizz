package br.com.mjv.quizz.infrastructure.persistence.repository;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.alternative.repository.AlternativesRepository;
import br.com.mjv.quizz.infrastructure.persistence.dao.AlternativeEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AlternativeRepositoryImpl implements AlternativesRepository {

    private final AlternativeEntityDao dao;

    @Override
    public Optional<Alternative> add(Alternative alternative) {
        var created = dao.save(alternative.toEntity());
        return Optional.of(created.toAlternative());
    }

    @Override
    public Optional<Alternative> update(Alternative alternative) {
        return Optional.empty();
    }

    @Override
    public Optional<Alternative> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Alternative> findAll() {
        return null;
    }

    @Override
    public Optional<Alternative> deleteById(UUID id) {
        return Optional.empty();
    }
}
