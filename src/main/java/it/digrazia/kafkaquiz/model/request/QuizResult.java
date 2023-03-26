package it.digrazia.kafkaquiz.model.request;

import java.util.List;

public record QuizResult(Long keycloakUserId, String username, Integer result, String date) {
    public static QuizResult withDefault(Long keycloakUserId, String username, Integer result, String date)  {

        return new QuizResult(keycloakUserId, username, result, date);
    }
}
