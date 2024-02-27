package br.com.mjv.quizz.domain.question.repository;

import br.com.mjv.quizz.domain.config.repository.Repository;
import br.com.mjv.quizz.domain.question.Question;

import java.util.UUID;

public interface QuestionsRepository extends Repository<Question, UUID> {
}
