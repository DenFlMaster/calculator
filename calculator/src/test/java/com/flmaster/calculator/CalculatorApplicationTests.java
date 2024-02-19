package com.flmaster.calculator;

import com.flmaster.calculator.model.CategoryModel;
import com.flmaster.calculator.repo.CategoryRepository;
import com.flmaster.calculator.request.UpdateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CalculatorApplicationTests {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
//		categoryRepository.insertCategory(
//				new UpdateCategoryRequest("Popaaaa")
//		);
//
//		List<CategoryModel> categories = categoryRepository.findCategories();
//
//		System.out.println(categories);
	}
}
