package it.digrazia.kafkaquiz.config;

import it.digrazia.kafkaquiz.model.enums.OpenAiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

@Component
public class OpenAiApiClient {

    @Value("${openai.api_key}")
    private String openaiApiKey;


    public OpenAiApiClient() {
    }

    private final HttpClient client = HttpClient.newHttpClient();

    public String postToOpenAiApi(String requestBodyAsJson, OpenAiServices service)
            throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(selectUri(service))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .POST(BodyPublishers.ofString(requestBodyAsJson)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private URI selectUri(OpenAiServices service) {
        return URI.create(switch (service) {
            case DALL_E -> "https://api.openai.com/v1/images/generations";
			case GPT_3_COMPLETIONS -> "https://api.openai.com/v1/completions";
			case GPT_3_CHAT_COMPLETIONS -> "https://api.openai.com/v1/chat/completions";
			case GPT_3_TRANSCRIPTIONS -> "https://api.openai.com/v1/audio/transcriptions";
			case GPT_3_TRANSLATIONS -> "https://api.openai.com/v1/audio/translations";
			case GPT_3_FINE_TUNES -> "https://api.openai.com/v1/fine-tunes";
			case GPT_3_EMBEDDINGS -> "https://api.openai.com/v1/embeddings";
			case GPT_3_MODERATIONS -> "https://api.openai.com/v1/moderations";
        });
    }

}
