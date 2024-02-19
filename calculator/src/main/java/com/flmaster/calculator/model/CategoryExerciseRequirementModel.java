package com.flmaster.calculator.model;

public record CategoryExerciseRequirementModel(
        int categoryId,
        int exerciseId,
        int count,
        String exerciseName
) {
}
