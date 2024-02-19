package com.flmaster.calculator.request;

public record UpdateCategoryRequest(
        String name,
        int level,
        int requirementMinCombo,
        int requirementMinElementsOfSameLevel,
        int requirementMinFemaleScore,
        int requirementMinMaleScore
) {
}
