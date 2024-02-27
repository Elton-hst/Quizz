package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Value {
    private String messaging_product;
    private Metadata metadata;
    private List<Contact> contacts;
    private List<Message> messages;
}