package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    private String id;
    private List<Change> changes;
}