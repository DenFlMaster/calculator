package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.ExerciseModel;
import com.flmaster.calculator.model.ExerciseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseResponse convert(ExerciseModel model);
}
