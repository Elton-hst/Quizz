package br.com.mjv.quizz.infrastructure.persistence.dao;

import br.com.mjv.quizz.infrastructure.persistence.entity.QuizEntity;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.QuizDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizEntityDao extends JpaRepository<QuizEntity, UUID> {
    Optional<QuizEntity> findByCategoryAndDifficulty(String category, QuizDifficulty difficulty);
    List<QuizEntity> findByCategory(String category);
}
