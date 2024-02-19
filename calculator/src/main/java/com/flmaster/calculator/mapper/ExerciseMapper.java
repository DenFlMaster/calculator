package com.flmaster.calculator.mapper;

import com.flmaster.calculator.model.Exercise;
import com.flmaster.calculator.model.ExerciseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    Exercise convert(ExerciseModel model);
}
