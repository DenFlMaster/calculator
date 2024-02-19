package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.CategoryExerciseRequirementMapper;
import com.flmaster.calculator.model.CategoryExerciseRequirement;
import com.flmaster.calculator.repo.CategoryExerciseRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryExerciseRequirementService {
    private final CategoryExerciseRequirementRepository repo;
    private final CategoryExerciseRequirementMapper mapper;
    private final CategoryService categoryService;
    private final ExerciseService exerciseService;


    public Optional<CategoryExerciseRequirement> findCategoryExerciseRequirement(Integer categoryId, Integer exerciseId) {
        return repo.findCategoryExerciseRequirement(categoryId, exerciseId).map(mapper::convert);
    }

    private void validateRequest(CategoryExerciseRequirement request){
        if (!(request.getCount() > 0)){
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    public CategoryExerciseRequirement setCategoryExerciseRequirement(
            Integer categoryId,
            Integer exerciseId,
            CategoryExerciseRequirement request
    ) {
        validateRequest(request);
        categoryService.findCategory(categoryId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.CATEGORY_NOT_FOUND));
        exerciseService.findExercise(exerciseId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        return mapper.convert(repo.setCategoryExerciseRequirement(categoryId, exerciseId, request));
    }

    public void deleteCategoryExerciseRequirement(
            Integer categoryId,
            Integer exerciseId
    ) {
        categoryService.findCategory(categoryId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.CATEGORY_NOT_FOUND));
        exerciseService.findExercise(exerciseId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        repo.deleteCategoryExerciseRequirement(categoryId, exerciseId);
    }

}
