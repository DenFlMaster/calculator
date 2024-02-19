package com.flmaster.calculator.response;

public record CategoryExerciseRequirementResponseDto(
        long categoryId,
        long exerciseId,
        int count
) {
}
