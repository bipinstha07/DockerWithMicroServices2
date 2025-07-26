package com.springboot.quizesMongo.quizesMongo.services;

import com.springboot.quizesMongo.quizesMongo.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "category-service",url = "http://localhost:9091/api/v1")
public interface CategoryServiceFeign {

    @GetMapping("/categories/getAll")
    List<CategoryDto> findAll();

}
