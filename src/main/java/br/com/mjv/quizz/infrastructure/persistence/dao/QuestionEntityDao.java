package br.com.mjv.quizz.infrastructure.persistence.dao;

import br.com.mjv.quizz.infrastructure.persistence.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionEntityDao extends JpaRepository<QuestionEntity, UUID> {
}
