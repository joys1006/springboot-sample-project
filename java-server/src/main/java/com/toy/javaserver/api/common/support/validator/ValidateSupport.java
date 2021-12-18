package com.toy.javaserver.api.common.support.validator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.common.utils.reflect.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * 공통 벨리데이션 체크 로직 필요한 경우 더 추가
 */
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateSupport {
    /**
     * 리퀘스트 요청 값에 대한 전체 NULL 체크 후 Exception 실행
     */
    public void modelFieldsValuesNullException(Object model) {
        Class<?> vo = model.getClass();
        Field[] fields = vo.getDeclaredFields();

        if (!Objects.isNull(fields)) {
            boolean isAllFieldsNull = ReflectUtil.isAllFieldsNull(model);

            if (isAllFieldsNull) {
                throw new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * 디스크립션 어노테이션이 빠져있는지에 대한 로깅
     */
    public void customPropertyValidateLogging(Object model) {
        Class vo = model.getClass();
        Field[] fields = vo.getDeclaredFields();

        Arrays.stream(fields)
                .forEach(field -> {
                    CustomProperty description = field.getAnnotation(CustomProperty.class);

                    if (Objects.isNull(description)) {
                        log.warn("[" + field.getName() + "] 필드에 CustomProperty 어노테이션이 누락 되었습니다.");
                    }
                });
    }
}
