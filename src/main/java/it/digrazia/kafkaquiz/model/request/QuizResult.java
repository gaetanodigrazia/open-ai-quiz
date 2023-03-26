package it.digrazia.kafkaquiz.model.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
@Entity
public class QuizResult {
    @Id
    private Long id;
    private String keycloakUserId;
    private String username;
    private Integer result;
    private String date;
    public QuizResult()  {

    }
    public QuizResult(String keycloakUserId, String username, Integer result, String date)  {
        setKeycloakUserId(keycloakUserId);
        setUsername(username);
        setResult(result);
        setDate(date);
    }
    public String getKeycloakUserId() {
        return keycloakUserId;
    }

    public void setKeycloakUserId(String keycloakUserId) {
        this.keycloakUserId = keycloakUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
