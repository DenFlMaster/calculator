package com.flmaster.calculator.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
