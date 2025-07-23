package com.springboot.quizesMongo.quizesMongo.services;

import com.springboot.quizesMongo.quizesMongo.collections.Quiz;
import com.springboot.quizesMongo.quizesMongo.dto.CategoryDto;
import com.springboot.quizesMongo.quizesMongo.dto.QuizDto;
import com.springboot.quizesMongo.quizesMongo.respository.QuizMongoRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizServiceImp implements QuizService{

    private  final Logger logger = LoggerFactory.getLogger(QuizServiceImp.class);
    private ModelMapper modelMapper;
    private QuizMongoRepo quizMongoRepo;
    private RestTemplate restTemplate;
    private  CategoryService categoryService;

    @Override
    public QuizDto create(QuizDto quizDto) {
        Quiz quiz = modelMapper.map(quizDto,Quiz.class);
        quiz.setId(UUID.randomUUID().toString());

        String url ="http://localhost:9091/api/v1/categories/"+ quizDto.getCategoryId();
        CategoryDto categoryDto = restTemplate.getForObject(url, CategoryDto.class);
        logger.info("Successfully Found Category ID");

        Quiz savedQuiz = quizMongoRepo.save(quiz);
        QuizDto savedQuizDto = modelMapper.map(savedQuiz,QuizDto.class);
        savedQuizDto.setCategoryDto(categoryDto);
        return savedQuizDto;

    }

    @Override
    public QuizDto update(String quizId, QuizDto quizDto) {
       Quiz quiz = quizMongoRepo.findById(quizId).orElseThrow(()-> new RuntimeException("No quiz Found"));
        quiz.setDescription(quizDto.getDescription());
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setMaxMarks(quizDto.getMaxMarks());
        quiz.setTimeLimit(quizDto.getTimeLimit());
        quiz.setCreatedBy(quizDto.getCreatedBy());
        quiz.setNoOfQuestions(quizDto.getNoOfQuestions());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setLive(quizDto.getLive());
        quiz.setPassingMarks(quizDto.getPassingMarks());
        quiz.setCategoryId(quizDto.getCategoryId());



        Quiz savedQuiz = quizMongoRepo.save(quiz);
        return modelMapper.map(savedQuiz,QuizDto.class);
    }

    @Override
    public void delete(String quizId) {
        Quiz quiz = quizMongoRepo.findById(quizId).orElseThrow(()-> new RuntimeException("No quiz Found"));
        quizMongoRepo.delete(quiz);
    }

    @Override
    public List<QuizDto> findAll() {
        List<Quiz> quiz = quizMongoRepo.findAll();

       List<QuizDto> quizDto1 =  quiz.stream().map((a)->{
           String categoryId = a.getCategoryId();
           QuizDto quizDto = modelMapper.map(a,QuizDto.class);

           //call to quiz service using webclient
           CategoryDto categoryDto = categoryService.findById(categoryId);
           quizDto.setCategoryDto(categoryDto);
           return quizDto;

        }).toList();


        return quizDto1;
    }

    @Override
    public QuizDto findById(String quizID) {
        Quiz quiz = quizMongoRepo.findById(quizID).orElseThrow(()-> new RuntimeException("No quiz Found"));
        QuizDto quizDto= modelMapper.map(quiz,QuizDto.class);
        String categoryId = quiz.getCategoryId();
        String url ="http://localhost:9091/api/v1/categories/"+categoryId;
        logger.info(url);
        CategoryDto categoryDto = restTemplate.getForObject(url, CategoryDto.class);
        quizDto.setCategoryDto(categoryDto);

        return quizDto;
    }

    @Override
    public List<QuizDto> findByCategory(String categoryId) {
        List<Quiz> quiz = quizMongoRepo.findByCategoryId(categoryId);
        List<QuizDto> quizDto = quiz.stream().map((a)->modelMapper.map(a,QuizDto.class)).toList();
        return quizDto;
    }
}
