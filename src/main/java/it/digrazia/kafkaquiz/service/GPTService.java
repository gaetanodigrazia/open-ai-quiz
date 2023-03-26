package it.digrazia.kafkaquiz.service;


import it.digrazia.kafkaquiz.model.request.CompletionRequest;
import it.digrazia.kafkaquiz.model.request.Question;
import it.digrazia.kafkaquiz.model.request.QuizResult;
import it.digrazia.kafkaquiz.model.response.CompletionResponse;

import java.util.List;

public interface GPTService {
    String sendCompletionRequest(CompletionRequest completionRequest) throws Exception;
    Question getQuestion() throws Exception;
    List<Question> getQuiz() throws Exception;
    List<QuizResult> getAllQuizResult();

    Boolean saveQuizResult(QuizResult user);
    CompletionResponse sendCompletionRequestGetResponse(CompletionRequest completionRequest) throws Exception;
}
