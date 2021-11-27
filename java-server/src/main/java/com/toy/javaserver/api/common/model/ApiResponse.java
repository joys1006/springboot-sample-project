package com.toy.javaserver.api.common.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * API 응답 변환 Entity
 * @param <T> 변환 타입
 */
public class ApiResponse<T> implements IApiResponse, Serializable {
    private static final long serialVersionUID = -2790327574322809168L;

    private T data;

    @Getter
    @Accessors(fluent = true)
    @JsonIgnore
    private transient final HttpStatus httpStatus;

    @Getter
    @Accessors(fluent = true)
    @JsonIgnore
    private transient final HttpHeaders headers;

    public ApiResponse(@NotNull T data) { this(data, HttpStatus.OK); }

    public ApiResponse(@NotNull T data, @NotNull HttpStatus httpStatus) { this(data, httpStatus, new HttpHeaders()); }

    public ApiResponse(@NotNull T data, @NotNull HttpStatus httpStatus, @NotNull HttpHeaders headers) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.headers = headers;
    }

    @JsonGetter("data")
    public T getData() { return data; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSetter("data")
    void setData(T data) { this.data = data; }
}
