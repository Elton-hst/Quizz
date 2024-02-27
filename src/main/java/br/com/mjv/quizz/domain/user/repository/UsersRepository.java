package br.com.mjv.quizz.domain.user.repository;

import br.com.mjv.quizz.domain.config.repository.Repository;
import br.com.mjv.quizz.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends Repository<User, UUID> {
    Optional<User> findByPhone(String phone);
}
