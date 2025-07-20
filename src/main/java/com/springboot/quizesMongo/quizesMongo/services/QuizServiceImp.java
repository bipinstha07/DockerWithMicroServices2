package com.springboot.quizesMongo.quizesMongo.services;

import com.springboot.quizesMongo.quizesMongo.collections.Quiz;
import com.springboot.quizesMongo.quizesMongo.dto.QuizDto;
import com.springboot.quizesMongo.quizesMongo.respository.QuizMongoRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizServiceImp implements QuizService{

    private ModelMapper modelMapper;
    private QuizMongoRepo quizMongoRepo;

    @Override
    public QuizDto create(QuizDto quizDto) {
        Quiz quiz = modelMapper.map(quizDto,Quiz.class);
        quiz.setId(UUID.randomUUID().toString());
        Quiz savedQuiz = quizMongoRepo.save(quiz);
        return modelMapper.map(savedQuiz,QuizDto.class);

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
        List<QuizDto> quizDto = quiz.stream().map((q)->modelMapper.map(q,QuizDto.class)).toList();
        return quizDto;
    }

    @Override
    public QuizDto findById(String quizID) {
        Quiz quiz = quizMongoRepo.findById(quizID).orElseThrow(()-> new RuntimeException("No quiz Found"));
        return modelMapper.map(quiz,QuizDto.class);
    }

    @Override
    public List<QuizDto> findByCategory(String categoryId) {
        List<Quiz> quiz = quizMongoRepo.findByCategoryId(categoryId);
        List<QuizDto> quizDto = quiz.stream().map((a)->modelMapper.map(a,QuizDto.class)).toList();
        return quizDto;
    }
}
