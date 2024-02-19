package com.flmaster.calculator.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionCode {
    CATEGORY_NOT_FOUND("Разряд не найден", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_ALREADY_TAKEN("Такой разряд уже существует", HttpStatus.BAD_REQUEST),
    TYPE_NOT_FOUND("Тип элемента не найден", HttpStatus.BAD_REQUEST),
    TYPE_NAME_ALREADY_TAKEN("Такой тип элемента уже существует", HttpStatus.BAD_REQUEST),
    ELEMENT_NOT_FOUND("Элемент не найден", HttpStatus.BAD_REQUEST),
    ELEMENT_NAME_ALREADY_TAKEN("Такой элемент уже существует", HttpStatus.BAD_REQUEST),
    EXERCISE_NOT_FOUND("Упражнение не найдено", HttpStatus.BAD_REQUEST),
    EXERCISE_NAME_ALREADY_TAKEN("Такое упражнение уже существует", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("Ошибка валидации", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("Внутренняя ошибка", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED("Доступ запрещён", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST("Неверный запрос", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
