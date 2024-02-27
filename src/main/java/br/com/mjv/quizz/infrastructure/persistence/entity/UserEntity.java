package br.com.mjv.quizz.infrastructure.persistence.entity;

import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.BasicEntity;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BasicEntity {

    private String name;
    private String phone;
    private Double score;
    private ChatContext context;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<QuizEntity> quiz;
    @CreationTimestamp
    private LocalDateTime createAt;

    public User toUser() {
        return new User(name, phone, score, context);
    }

}
