package br.com.mjv.quizz.domain.user;

import br.com.mjv.quizz.domain.quiz.Quiz;
import br.com.mjv.quizz.infrastructure.persistence.entity.UserEntity;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;

public record User(
        String name,
        String phone,
        Double score,
        ChatContext context) {

    public User(String name, String phone, Double score, ChatContext context) {
        this.name = name;
        this.phone = phone;
        this.score = score;
        this.context = context;
    }

    public User(String phone, ChatContext context) {
        this(null, phone, null, context);
    }

    public User(String phone) {
        this(null, phone, null, null);
    }

    public User(String name, Double score, ChatContext chatContext) {
        this(name, null, score, chatContext);
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .phone(phone)
                .score(score)
                .context(context)
                .build();
    }

}
