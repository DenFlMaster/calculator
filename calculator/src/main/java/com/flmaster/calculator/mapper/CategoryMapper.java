package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CategoryExerciseRequirementMapper.class)
public interface CategoryMapper {
    CategoryResponse convert(CategoryModel model);
}
