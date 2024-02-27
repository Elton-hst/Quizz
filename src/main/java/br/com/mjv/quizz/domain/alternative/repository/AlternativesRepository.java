package br.com.mjv.quizz.domain.alternative.repository;

import br.com.mjv.quizz.domain.alternative.Alternative;
import br.com.mjv.quizz.domain.config.repository.Repository;

import java.util.UUID;

public interface AlternativesRepository extends Repository<Alternative, UUID> {
}
