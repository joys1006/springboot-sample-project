package com.toy.javaserver.api.common.utils.response;

import com.toy.javaserver.api.common.exception.AbstractApiException;
import com.toy.javaserver.api.common.model.ApiErrorResponse;
import com.toy.javaserver.api.common.model.ApiResponse;
import com.toy.javaserver.api.common.model.IApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice {
    private static final Marker API_ERROR_MARKER = MarkerFactory.getMarker("API_ERROR");

    private String[] includes;

    static AntPathMatcher matcher = new AntPathMatcher(".");

    @Autowired(required = false)
    WebMvcProperties webMvcProperties;

    @Autowired(required = false)
    DispatcherServlet dispatcherServlet;

    public ApiResponseBodyAdvice(String[] includes) { this.includes = includes; }

    /**
     * 404 Not Found
     * @return ErrorAttributes
     */
    @Bean
    public ErrorAttributes notFoundErrorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions includeStackTrace) {

                Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);

                Object error = attributes.get("error");

                if (error == null) {
                    error = attributes.get("error");

                    if (error == null) {
                        error = "Not Found";
                    }
                }

                Map<String, Object> result = new HashMap<>();

                result.put("error", new ApiErrorResponse(error.toString()).getError());

                return result;
            }
        };
    }

    /**
     * method ?????? ????????? String ?????? ApiResponse??? ???????????? ?????? ????????? ?????? ??????
     */
    @Configuration
    protected static class FixedStringMessageConverter implements WebMvcConfigurer {
        /**
         * StringHttpMessageConverter ??? ?????? ?????? ??????
         * @param converters ????????? Converter
         */
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            List<HttpMessageConverter<?>> messageConverters = converters.stream()
                    .sorted((o1, o2) -> {
                        if (o1 instanceof StringHttpMessageConverter) {
                            return 1;
                        }
                        if (o2 instanceof StringHttpMessageConverter) {
                            return 2;
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());

            converters.clear();
            converters.addAll(messageConverters);
        }
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class<?> containingClass = returnType.getContainingClass();
        String canonicalName = containingClass.getCanonicalName();

        for (int i = 0; i < includes.length; i++) {
            if (matcher.match(includes[i], canonicalName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse httpResponse) {
        IApiResponse response;
        HttpHeaders headers = httpResponse.getHeaders();

        if (body instanceof  IApiResponse) {
            response = (IApiResponse) body;
            HttpStatus httpStatus = response.httpStatus();

            if (!HttpStatus.OK.equals(httpStatus)) {
                try {
                    httpResponse.setStatusCode(httpStatus);
                } catch (Exception e) { log.error(e.getMessage()); }
            }

            if (response.headers() != null) response.headers().forEach((s, s2) -> headers.addAll(s, s2));
        } else {
            response = new ApiResponse(body);
        }

        // trace id header ??????
        if (!headers.containsKey("X-Trace-Id")) {
            UUID reandomUUID = UUID.randomUUID();
            String uuid = reandomUUID.toString();

            headers.add("X-Trace-Id", uuid);
        }

        return response;
    }

    /**
     * Exception ??????
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public static ApiErrorResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        String message = "";
        // unknown code
        int code = Integer.MIN_VALUE;

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        // AbstractApiException??? ???????????? ????????? Exception??? ??????
        if (ex instanceof AbstractApiException) {
            AbstractApiException apiException = (AbstractApiException) ex;

            httpStatus = apiException.getHttpStatus();
            code = apiException.getCode();
        } else if (ex instanceof NoHandlerFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);

            if (status != null) {
                httpStatus = status.value();
                message = status.reason();
            }
        }

        if (message == null || message.isEmpty()) {
            message = ex.getLocalizedMessage();
            if (message == null || message.isEmpty()) {
                message = ex.getMessage();
            }
        }

        String errorURL = request.getRequestURL().toString();
        UUID randomUUID = UUID.randomUUID();
        String uuid = randomUUID.toString();

        response.addHeader("X-Trace-Id", uuid);
        response.addHeader("X-Error-Url", errorURL);

        String hostName = _HostName();

        log.error(API_ERROR_MARKER, "[{}], {}, {}", uuid, hostName, errorURL, ex);

        if (!HttpStatus.OK.equals(httpStatus)) {
            try {
                response.setStatus(httpStatus.value());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        ApiErrorResponse apiResponse = new ApiErrorResponse(message, code, httpStatus);

        return apiResponse;
    }

    private static String _HostName() {
        try {
            String result = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }

        String host = System.getenv("COMPUTERNAME");

        if (host != null) return host;

        host = System.getenv("HOSTNAME");

        if (host != null) return host;

        return null;
    }
}
