package br.com.mjv.quizz.infrastructure.web.dto;

import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto {

    private String text;
    private String phone;
    private String name;

}
