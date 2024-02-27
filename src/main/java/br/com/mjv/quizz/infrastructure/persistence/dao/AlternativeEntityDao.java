package br.com.mjv.quizz.infrastructure.persistence.dao;

import br.com.mjv.quizz.infrastructure.persistence.entity.AlternativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlternativeEntityDao extends JpaRepository<AlternativeEntity, UUID> {
}
