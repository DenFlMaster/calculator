package com.flmaster.calculator.model;

import java.util.List;

public record CategoryModel(
        int id,
        String name,
        int level,
        Requirements requirements,
        List<CategoryExerciseRequirementModel> exerciseRequirements
) {
    public record Requirements(
            int minCombo,
            int minElementsOfSameLevel,
            int minFemaleScore,
            int minMaleScore
    ) {}


}
