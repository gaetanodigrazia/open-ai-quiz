package it.digrazia.kafkaquiz.repository;


import it.digrazia.kafkaquiz.model.request.Quiz;
import it.digrazia.kafkaquiz.model.request.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The Interface QuizResultRepository.
 */
public interface QuizResultRepository<T> extends JpaRepository<QuizResult, T> {

}