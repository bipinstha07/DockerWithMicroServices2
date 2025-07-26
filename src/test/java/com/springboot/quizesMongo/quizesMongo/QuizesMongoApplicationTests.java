package com.springboot.quizesMongo.quizesMongo;

import com.springboot.quizesMongo.quizesMongo.dto.CategoryDto;
import com.springboot.quizesMongo.quizesMongo.services.CategoryServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class QuizesMongoApplicationTests {

	@Autowired
	private CategoryServiceFeign categoryServiceFeign;

	@Test
	public void testFeignAll() {
		List<CategoryDto> categoryDtos = categoryServiceFeign.findAll();
		categoryDtos.forEach(categoryDto -> System.out.println(categoryDto.getTitle()));
		Assertions.assertEquals(3,categoryDtos.size());
	}

}
