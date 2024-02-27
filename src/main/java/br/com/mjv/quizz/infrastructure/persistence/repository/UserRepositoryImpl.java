package br.com.mjv.quizz.infrastructure.persistence.repository;

import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import br.com.mjv.quizz.infrastructure.persistence.dao.UserEntityDao;
import br.com.mjv.quizz.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UsersRepository {

    private final UserEntityDao dao;

    @Override
    public Optional<User> add(User user) {
        var created = dao.save(user.toEntity());
        return Optional.ofNullable(created.toUser());
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return dao.findByPhone(phone).map(UserEntity::toUser);
    }

    @Override
    public Optional<User> update(User user) {
        Optional<UserEntity> existingUserOptional = dao.findByPhone(user.phone());

        if (existingUserOptional.isEmpty()) {
            return Optional.empty();
        }

        UserEntity existingUser = existingUserOptional.get();

        existingUser.setContext(user.context());
        existingUser.setCreateAt(LocalDateTime.now());

        UserEntity updatedUserEntity = dao.save(existingUser);
        return Optional.ofNullable(updatedUserEntity.toUser());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> deleteById(UUID id) {
        return Optional.empty();
    }
}
