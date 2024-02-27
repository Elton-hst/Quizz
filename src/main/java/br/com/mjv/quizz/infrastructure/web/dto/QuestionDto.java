package br.com.mjv.quizz.infrastructure.web.dto;

public record QuestionDto(
        String theme,
        String question,
        ResponseDto response) {
}
