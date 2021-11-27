package com.toy.javaserver.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import static com.toy.javaserver.api.common.support.DateSupport.LOCAL_DATE_TIME_FORMATTER;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper serializing() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // LocalDateTime 아래 포멧팅 형태로 변환
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(LOCAL_DATE_TIME_FORMATTER);

        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        // 모듈 등록
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

}
