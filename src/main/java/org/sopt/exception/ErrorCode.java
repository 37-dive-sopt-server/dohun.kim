package org.sopt.exception;

public interface ErrorCode {
    int getStatusCode();
    int getExceptionCode();
    String getMessage();
}
