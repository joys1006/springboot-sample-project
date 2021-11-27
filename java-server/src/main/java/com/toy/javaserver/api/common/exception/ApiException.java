package com.toy.javaserver.api.common.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends AbstractApiException {
    private static final long serialVersionUID = -7823800670506884689L;

    public ApiException(String message) { super(message, HttpStatus.INTERNAL_SERVER_ERROR, null); }

    public ApiException(String message, Throwable cause) { super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause); }

    public ApiException(String message, HttpStatus httpStatus) { super(message, httpStatus, null); }

    public ApiException(String message, HttpStatus httpStatus, int code) { super(message, httpStatus, code); }

    public ApiException(String message, HttpStatus httpStatus, Throwable cause) { super(message, httpStatus, cause); }

    public ApiException(String message, int code) { super(message, code); }

    public ApiException(String message, int code, Throwable cause) { super(message, code, cause); }

    public ApiException(String message, HttpStatus httpStatus, int code, Throwable cause) {
        super(message, httpStatus, code, cause);
    }
}
