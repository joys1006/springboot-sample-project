package com.toy.javaserver.api.common.support.validator;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Slf4j
public class CustomPropertyValidator implements ConstraintValidator<CustomProperty, Object> {
    private Boolean nullable;
    private String title;
    private String message;

    @Override
    @NotNull
    public void initialize(CustomProperty contraintAnnotation) {
        nullable = contraintAnnotation.nullable();
        title = contraintAnnotation.value();
        message = contraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!nullable & Objects.isNull(value)) {
            addConstraintViolation(context, "[" + title + "] 값은 필수 요청 값 입니다.");
        } else {
            addConstraintViolation(context, "[" + title + "]" + message);
        }

        return false;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
        // 기본 메세지 활성화
        context.disableDefaultConstraintViolation();
        // 새로운 메세지 추가
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
}
