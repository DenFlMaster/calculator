package com.flmaster.calculator.response;

public record ElementResponseDto(
        long id,
        String name,
        int level,
        int score,
        long type_id
) {
}
