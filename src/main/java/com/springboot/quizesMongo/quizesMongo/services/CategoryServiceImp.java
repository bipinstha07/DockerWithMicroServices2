package com.springboot.quizesMongo.quizesMongo.services;

import com.springboot.quizesMongo.quizesMongo.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService{

    private WebClient webClient;


    @Override
    public CategoryDto findById(String categoryId) {
     return   this.webClient
                .get()
                .uri("/api/v1/categories/{categoryId}",categoryId)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }

    @Override
    public List<CategoryDto> findAll() {
        return this.webClient
                .get()
                .uri("api/v1/categories/getAll")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
       return this.webClient
               .post()
               .uri("/api/v1/categories")
               .bodyValue(categoryDto)
               .retrieve()
               .bodyToMono(CategoryDto.class)
               .block();
    }

    @Override
    public CategoryDto update(String categoryId, CategoryDto categoryDto) {
        return this.webClient
                .put()
                .uri("/api/v1/categories/{categoryId}",categoryId)
                .bodyValue(categoryDto)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }

    @Override
    public void delete(String categoryId) {
        this.webClient
                .delete()
                .uri("api/v1/categories/{categoryId}",categoryId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
