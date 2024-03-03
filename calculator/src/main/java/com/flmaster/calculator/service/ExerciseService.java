package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ExerciseMapper;
import com.flmaster.calculator.model.ExerciseRequest;
import com.flmaster.calculator.model.ExerciseResponse;
import com.flmaster.calculator.repo.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repo;
    private final ExerciseMapper mapper;

    public List<ExerciseResponse> findExercises() {
        return repo.findExercises().stream().map(mapper::convert).toList();
    }

    public Optional<ExerciseResponse> findExercise(long id) {
        return repo.findExercise(id).map(mapper::convert);
    }

    private void validateRequest(ExerciseRequest request){
        if (!(StringUtils.hasText(request.getName()))){
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    private void checkExerciseName(String name){
        if(repo.findExerciseByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.EXERCISE_NAME_ALREADY_TAKEN);
    }

    public ExerciseResponse updateExercise(long id, ExerciseRequest request) {
        validateRequest(request);
        var exercise = findExercise(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        if(!Objects.equals(exercise.getName(), request.getName())){
            checkExerciseName(request.getName());
        }
        return mapper.convert(repo.updateExercise(id, request));
    }

    public ExerciseResponse insertExercise(ExerciseRequest request) {
        validateRequest(request);
        checkExerciseName(request.getName());
        return mapper.convert(repo.insertExercise(request));
    }

    public void deleteExercise(Integer id) {
        findExercise(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        repo.deleteExercise(id);
    }

}
