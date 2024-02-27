package br.com.mjv.quizz.infrastructure.persistence.dao;

import br.com.mjv.quizz.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityDao extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhone(String phone);
}
