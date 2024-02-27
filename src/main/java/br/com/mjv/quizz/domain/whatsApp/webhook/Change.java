package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Change {
    private Value value;
    private String field;
}