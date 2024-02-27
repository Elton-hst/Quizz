package br.com.mjv.quizz.domain.config.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T, ID> {

    Optional<T> add(T t);
    Optional<T> update(T t);
    Optional<T> findById(UUID id);
    List<T> findAll();
    Optional<T> deleteById(UUID id);

}
