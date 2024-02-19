package com.flmaster.calculator.exception;

import com.flmaster.calculator.model.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(BusinessException businessException) {
        return buildResponseEntity(businessException, businessException.getCode().getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(BusinessException businessException, String message) {
        return new ResponseEntity<>(new ErrorResponse(
                businessException.getCode().name(),
                message
        ), businessException.getCode().getStatus());
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleError(Throwable throwable) {
        if (throwable instanceof BusinessException be) {
            return buildResponseEntity(be);
        }
        if (throwable instanceof AuthenticationException be) {
            return buildResponseEntity(new BusinessException(BusinessExceptionCode.UNAUTHORIZED), be.getMessage());
        }
        if (throwable instanceof HttpMessageConversionException be) {
            return buildResponseEntity(new BusinessException(BusinessExceptionCode.BAD_REQUEST));
        }
        throwable.printStackTrace();
        return buildResponseEntity(new BusinessException(BusinessExceptionCode.INTERNAL_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleError(ex);
    }
}
