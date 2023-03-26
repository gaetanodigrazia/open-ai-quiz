package it.digrazia.kafkaquiz.model.response;

import java.util.List;
import java.util.Optional;

public record CompletionResponse(String id, String object, String created, String model, Usage usage, List<Choice> choices) {

	public Optional<String> firstAnswer() {
		if (choices == null || choices.isEmpty())
			return Optional.empty();
		return Optional.of(choices.get(0).text);
	}

	record Usage(int total_tokens, int prompt_tokens, int completion_tokens) {}
	
	record Choice(String text, int index, String logprobs, String finish_reason) {
	}
}
