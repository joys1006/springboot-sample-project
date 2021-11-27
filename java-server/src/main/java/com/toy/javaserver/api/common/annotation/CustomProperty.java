package com.toy.javaserver.api.common.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 필드에 필요한 기능 추가시 커스텀하게 사용할 용도
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomProperty {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String value() default "";
    String comment() default "";
    String example() default "";
    boolean nullable() default true;
}
