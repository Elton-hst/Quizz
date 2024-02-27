package br.com.mjv.quizz.domain.user.dto;

import br.com.mjv.quizz.domain.quiz.dto.CreateUpdateQuizDto;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;

public record CreateUpdateUserDto(
        String name,
        String phone,
        Double score,
        ChatContext context,
        CreateUpdateQuizDto quizDto) {

    public CreateUpdateUserDto(String name, String phone, Double score, ChatContext context, CreateUpdateQuizDto quizDto) {
        this.name = name;
        this.phone = phone;
        this.score = score;
        this.context = context;
        this.quizDto = quizDto;
    }

    public CreateUpdateUserDto(String phone, ChatContext context) {
        this(null, phone, null, context, null);
    }

    public CreateUpdateUserDto(String phone, Double score) {
        this(null, phone, score, null, null);
    }

    public CreateUpdateUserDto(String name, String phone) {
        this(name, phone, null, null, null);
    }
}
