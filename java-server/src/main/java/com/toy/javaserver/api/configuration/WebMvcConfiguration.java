package com.toy.javaserver.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.javaserver.api.common.converter.StringToEnumConverterFactory;
import com.toy.javaserver.api.common.support.JsonXssProtectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Bean
    public WebMvcConfigurerAdapter controlTowerWebConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                super.configureMessageConverters(converters);

                // WebMvcConfigurerAdapter에 MessageConverter 추가
                converters.add(htmlEscapingConveter());
            }

            private HttpMessageConverter<?> htmlEscapingConveter() {
                ObjectMapper objectMapper = new ObjectMapper();
                // ObjectMapper에 특수 문자 처리 기능 적용
                objectMapper.getFactory().setCharacterEscapes(new JsonXssProtectSupport());

                // MessageConverter에 ObjectMapper 설정
                MappingJackson2HttpMessageConverter htmlEscapingConverter =
                        new MappingJackson2HttpMessageConverter();
                htmlEscapingConverter.setObjectMapper(objectMapper);

                return htmlEscapingConverter;
            }
        };
    }

    @Bean
    public RequestContextListener requestContextListener() { return new RequestContextListener(); }

}
