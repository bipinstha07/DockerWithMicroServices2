package com.springboot.quizesMongo.quizesMongo.respository;

import com.springboot.quizesMongo.quizesMongo.collections.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizMongoRepo extends MongoRepository<Quiz,String> {

    List<Quiz> findQuizByTitle(String title);

}
