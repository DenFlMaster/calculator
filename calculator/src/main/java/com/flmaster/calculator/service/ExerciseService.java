package com.flmaster.calculator.service;

import com.flmaster.calculator.exception.BusinessException;
import com.flmaster.calculator.exception.BusinessExceptionCode;
import com.flmaster.calculator.mapper.ExerciseMapper;
import com.flmaster.calculator.model.Exercise;
import com.flmaster.calculator.repo.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repo;
    private final ExerciseMapper mapper;

    public List<Exercise> findExercises() {
        return repo.findExercises().stream().map(mapper::convert).toList();
    }

    public Optional<Exercise> findExercise(long id) {
        return repo.findExercise(id).map(mapper::convert);
    }

    private void validateRequest(Exercise request){
        if (!(request.getName().length() > 0)){
            throw new BusinessException(BusinessExceptionCode.VALIDATION_ERROR);
        }
    }

    private void checkExerciseName(String name){
        if(repo.findExerciseByName(name).isPresent())
            throw new BusinessException(BusinessExceptionCode.EXERCISE_NAME_ALREADY_TAKEN);
    }

    public Exercise updateExercise(long id, Exercise request) {
        validateRequest(request);
        findExercise(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        checkExerciseName(request.getName());
        return mapper.convert(repo.updateExercise(id, request));
    }

    public Exercise insertExercise(Exercise request) {
        validateRequest(request);
        checkExerciseName(request.getName());
        return mapper.convert(repo.insertExercise(request));
    }

    public void deleteExercise(long id) {
        findExercise(id).orElseThrow(() -> new BusinessException(BusinessExceptionCode.EXERCISE_NOT_FOUND));
        repo.deleteExercise(id);
    }

}
