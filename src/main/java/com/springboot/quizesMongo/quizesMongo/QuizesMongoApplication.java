package com.springboot.quizesMongo.quizesMongo;

import com.springboot.quizesMongo.quizesMongo.collections.Quiz;
import com.springboot.quizesMongo.quizesMongo.respository.QuizMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class QuizesMongoApplication implements CommandLineRunner {


	@Autowired
	private QuizMongoRepo quizMongoRepo;

	public static void main  (String[] args) {
		SpringApplication.run(QuizesMongoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
	Quiz quiz =	Quiz.builder()
				.id(UUID.randomUUID().toString())
				.title("Java Quiz")
				.description("Quizes demo")
				.maxMarks(100)
				.timeLimit(60)
				.createdBy("Shrestha")
				.noOfQuestions(20)
				.imageUrl("htps:/iamge.url.demo")
				.live(true)
				.passingMarks(70)
				.build();

	 quizMongoRepo.save(quiz);
//		System.out.println(savedQuiz.getTitle());
	}
}
