package com.flmaster.calculator.response;

public record CategoryResponseDto(
        long id,
        String name,
        int level,
        int requirementMinCombo,
        int requirementMinElementsOfSameLevel,
        int requirementMinFemaleScore,
        int requirementMinMaleScore
) {
}
