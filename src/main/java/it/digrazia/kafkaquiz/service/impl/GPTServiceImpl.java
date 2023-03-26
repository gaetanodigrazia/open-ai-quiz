package it.digrazia.kafkaquiz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.digrazia.kafkaquiz.config.OpenAiApiClient;
import it.digrazia.kafkaquiz.model.enums.OpenAiServices;
import it.digrazia.kafkaquiz.model.request.CompletionRequest;
import it.digrazia.kafkaquiz.model.request.Question;
import it.digrazia.kafkaquiz.model.request.Quiz;
import it.digrazia.kafkaquiz.model.request.QuizResult;
import it.digrazia.kafkaquiz.model.response.CompletionResponse;
import it.digrazia.kafkaquiz.repository.QuizResultRepository;
import it.digrazia.kafkaquiz.service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GPTServiceImpl implements GPTService {
    private final ObjectMapper jsonMapper;
    private final OpenAiApiClient client;

    private final QuizResultRepository<QuizResult> quizRepository;
    @Value("${quiz.topic}")
    private String quizTopic;

    @Autowired
    public GPTServiceImpl(OpenAiApiClient openAiApiClient, QuizResultRepository<QuizResult> quizRepository){
        this.client = openAiApiClient;
        this.jsonMapper = new ObjectMapper();
        this.quizRepository = quizRepository;
    }

    @Override
    public String sendCompletionRequest(CompletionRequest completionRequest) throws Exception {
        CompletionRequest completion = this.selectBaseModel(completionRequest);

        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiServices.GPT_3_COMPLETIONS);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse.firstAnswer().orElseThrow();
    }

    @Override
    public Question getQuestion() throws Exception {
        return this.sendQuestionRequest();
    }


    @Override
    public List<Question> getQuiz() throws Exception {
        List<Question> quiz = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            quiz.add(getQuestion());
        }
        return quiz;
    }

    @Override
    public List<QuizResult> getAllQuizResult() {
        return quizRepository.findAll();
    }

    @Override
    public Boolean saveQuizResult(QuizResult quizResult) {
        quizRepository.save(quizResult);
        return null;
    }

    @Override
    public CompletionResponse sendCompletionRequestGetResponse(CompletionRequest completionRequest) throws Exception {
        CompletionRequest completion = this.selectBaseModel(completionRequest);

        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiServices.GPT_3_COMPLETIONS);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse;
    }
    final CompletionRequest selectBaseModel(CompletionRequest completionRequest) {
        switch (completionRequest.model().toString()) {
            case "davinci" -> {
                return CompletionRequest.withDaVinci(completionRequest.prompt());
            }
            case "curie" -> {
                return CompletionRequest.withCurie(completionRequest.prompt());
            }
            case "babbage" -> {
                return CompletionRequest.withBabbage(completionRequest.prompt());
            }
            case "ada" -> {
                return CompletionRequest.withAda(completionRequest.prompt());
            }
        }
        return CompletionRequest.withDaVinci(completionRequest.prompt());
    }

    public final Question sendQuestionRequest() throws Exception {
        CompletionRequest completion = CompletionRequest.withDaVinci("give me: a multiple " +
                "choice extremely random question with four possible answer about "+quizTopic+", the correct answer and thee explanation. Fill json schema for the result in this way" +
                ": {\n" +
                "    \"questionText\": null" +
                "    \"possibleAnswers\": [],\n" +
                "    \"rightAnswer\": null,\n" +
                "    \"explanation\": null\n" +
                "}");
        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiServices.GPT_3_COMPLETIONS);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        String completionResponseString  = completionResponse.firstAnswer().orElseThrow();
        System.out.println(completionResponseString);
        return jsonMapper.readValue(completionResponseString, Question.class);
    }

}
