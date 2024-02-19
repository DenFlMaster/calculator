package com.flmaster.calculator.model;

public record ElementModel(
        int id,
        String name,
        int level,
        int score,
        ElementTypeModel type
) {
}
