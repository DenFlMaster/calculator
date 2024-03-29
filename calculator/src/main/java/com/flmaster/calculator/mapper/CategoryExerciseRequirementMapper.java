package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.CategoryExerciseRequirementModel;
import com.flmaster.calculator.model.CategoryExerciseRequirementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryExerciseRequirementMapper {
    @Mapping(source = "model.exerciseId", target = "exercise.id")
    @Mapping(source = "model.exerciseName", target = "exercise.name")
    CategoryExerciseRequirementResponse convert(CategoryExerciseRequirementModel model);
}
