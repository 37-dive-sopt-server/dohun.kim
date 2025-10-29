package org.sopt.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.sopt.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.fail(errorCode.getExceptionCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String reason = extractReadableMessage(ex);
        String message = reason == null
                ? "요청 본문의 형식이 올바르지 않습니다."
                : "요청 본문의 형식이 올바르지 않습니다. 원인: " + reason;

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception ex) {
        System.err.println("Unexpected Exception: " + ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(ApiResponse.fail(500, "서버 내부 오류가 발생했습니다."));
    }

    private String extractReadableMessage(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            Object invalidValue = invalidFormatException.getValue();
            String value = invalidValue == null ? "null" : invalidValue.toString();
            return "'" + value + "' 값을 " +
                    invalidFormatException.getTargetType().getSimpleName() + "로 변환할 수 없습니다.";
        }
        if (cause instanceof com.fasterxml.jackson.core.JsonParseException parseException) {
            return "JSON 구문 오류: " + parseException.getOriginalMessage();
        }
        if (cause != null) {
            return cause.getMessage();
        }
        return null;
    }
}
