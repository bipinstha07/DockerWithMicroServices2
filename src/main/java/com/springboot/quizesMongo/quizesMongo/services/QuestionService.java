package com.springboot.quizesMongo.quizesMongo.services;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "question-service",url = "http://localhost:9093")
public interface QuestionService {


}
