package it.digrazia.kafkaquiz.model.request;

import java.util.List;

public record Quiz(List<Question> questionList) {
}
