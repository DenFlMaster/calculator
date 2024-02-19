package com.flmaster.calculator.request;

public record UpdateElementRequest(
        String name,
        int level,
        int score,
        long type_id
) {
}
