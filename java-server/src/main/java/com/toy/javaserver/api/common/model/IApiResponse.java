package com.toy.javaserver.api.common.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public interface IApiResponse {
    HttpStatus httpStatus();
    HttpHeaders headers();
}
