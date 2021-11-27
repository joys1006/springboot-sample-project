package com.toy.javaserver.api.common.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = Integer.MIN_VALUE;

    private HttpStatus httpStatus;

    public int getCode() { return code; }

    public void setCode(int code) { this.code = code; }

    public HttpStatus getHttpStatus() { return httpStatus; }

    public void setHttpStatus(HttpStatus httpStatus) { this.httpStatus = httpStatus; }

    public AbstractApiException(HttpStatus httpStatus) { this("", httpStatus); }

    public AbstractApiException(int code) { this("", code); }

    public AbstractApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AbstractApiException(String message, HttpStatus httpStatus, int code) {
        this(message, httpStatus);
        this.code = code;
    }

    public AbstractApiException(String message, int code) { this(message, HttpStatus.INTERNAL_SERVER_ERROR, code); }

    public AbstractApiException(String message, int code, Throwable cause) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, code, cause);
    }

    public AbstractApiException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause, false, false);
        this.httpStatus = httpStatus;
    }

    public AbstractApiException(String message, HttpStatus httpStatus, int code, Throwable cause) {
        this(message, httpStatus, cause);
        this.code = code;
    }

}
