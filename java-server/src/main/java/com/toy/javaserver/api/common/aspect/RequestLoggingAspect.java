package com.toy.javaserver.api.common.aspect;

import com.google.common.base.Joiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class RequestLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingAspect.class);

    /**
     * parameter Map to String Converter
     * @param paramMap 리퀘스트 파라미터
     * @return String 파라미터를 변환한 문자열
     */
    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    @Pointcut("within(com.toy.javaserver.api.controller..*)")
    public void onRequest() {}

    @Around("com.toy.javaserver.api.common.aspect.RequestLoggingAspect.onRequest()")
    public Object doLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "";

        if (paramMap.isEmpty() == false) {
            // 파라미터 로깅 출력 문
            params = " [" + paramMapToString(paramMap) + "] ";
        }

        long start = System.currentTimeMillis();

        try {
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } finally {
            long end = System.currentTimeMillis();
            // 로그 출력 (메소드, URI, HOST, 걸린 시간 ms)
            log.debug("Request: {} {}{} < {} {{}ms)", request.getMethod(), request.getRequestURI(), params, request.getRemoteHost(), end - start);
        }
    }
}
