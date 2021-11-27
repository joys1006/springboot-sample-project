package com.toy.javaserver.api.common.exception;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class CheckServiceException extends AbstractApiException {
    private static final long serialVersionUID = 8986333546037085367L;

    private static int code = Integer.MAX_VALUE;

    @Getter
    @Accessors(fluent = true)
    private Optional<LocalDateTime> start = Optional.empty();

    @Getter
    @Accessors(fluent = true)
    private Optional<LocalDateTime> end = Optional.empty();

    public CheckServiceException(String message) { super(message, code); }

    public CheckServiceException(String message, LocalDateTime start, LocalDateTime end) {
        super(message, code);

        this.start = Optional.ofNullable(start);
        this.end = Optional.ofNullable(end);
    }
}
