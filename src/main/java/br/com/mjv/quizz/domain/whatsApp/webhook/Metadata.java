package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private String display_phone_number;
    private String phone_number_id;
}