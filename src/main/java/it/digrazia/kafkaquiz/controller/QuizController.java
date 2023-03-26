package it.digrazia.kafkaquiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.digrazia.kafkaquiz.config.OpenAiApiClient;

import it.digrazia.kafkaquiz.model.request.CompletionRequest;
import it.digrazia.kafkaquiz.model.request.Question;
import it.digrazia.kafkaquiz.model.request.QuizResult;
import it.digrazia.kafkaquiz.model.response.CompletionResponse;
import it.digrazia.kafkaquiz.service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuizController {

	@Autowired private ObjectMapper jsonMapper;
	@Autowired private OpenAiApiClient client;

	@Autowired
	GPTService gptService;


	@PostMapping(path = "/response/complete")
	@ResponseBody
	public CompletionResponse completionRequestWithResponse(@RequestBody CompletionRequest completionRequest) throws Exception {
		return gptService.sendCompletionRequestGetResponse(completionRequest);
	}

	@PostMapping(path = "/response/answer")
	@ResponseBody
	public String completionRequestOnlyAnswer(@RequestBody CompletionRequest completionRequest) throws Exception {
		return gptService.sendCompletionRequest(completionRequest);
	}

	@GetMapping(path = "/new/question")
	@ResponseBody
	public Question newQuestion() throws Exception {
		return gptService.getQuestion();
	}

	@GetMapping(path = "/new/quiz")
	@ResponseBody
	public List<Question> newQuiz() throws Exception {
		return gptService.getQuiz();
	}

	@PostMapping(path = "/save/quiz/result")
	@ResponseBody
	public Boolean saveQuizResult(@RequestBody QuizResult user) throws Exception {
		return gptService.saveQuizResult(user);
	}

	@GetMapping(path = "/quiz/result")
	@ResponseBody
	public List<QuizResult> getAllQuizResult() throws Exception {
		return gptService.getAllQuizResult();
	}

	
}
