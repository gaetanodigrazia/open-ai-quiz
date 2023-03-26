package it.digrazia.kafkaquiz.model.request;

import java.util.List;

public record Question(String questionText, List<String> possibleAnswers, String rightAnswer, String explanation) {

    public static Question withDefault(String questionText, List<String> possibleAnswers, String rightAnswer, String explanation) {

        return new Question(questionText, possibleAnswers, rightAnswer, explanation);
    }
}
