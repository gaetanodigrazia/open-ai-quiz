package it.digrazia.kafkaquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class KafkaQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaQuizApplication.class, args);
	}

}
