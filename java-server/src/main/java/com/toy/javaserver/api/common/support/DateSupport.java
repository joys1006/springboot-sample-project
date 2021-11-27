package com.toy.javaserver.api.common.support;

import com.toy.javaserver.api.configuration.JacksonConfiguration;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateSupport {
    /**
     * @see JacksonConfiguration
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"));
}
