package com.toy.javaserver.api.common.annotation;

import com.toy.javaserver.api.common.utils.response.ApiResponseImportRegistrar;
import com.toy.javaserver.api.configuration.WebMvcConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(ApiResponseImportRegistrar.class)
@AutoConfigureAfter(WebMvcConfiguration.class)
public @interface EnableApiResponse {

    /**
     * ApiResponse를 제외할 패키지
     * @return 제외 패키지
     */
    @Deprecated
    String[] excludes() default {};

    /**
     * ApiResponse를 포함할 패키지
     * @return 포함 패키지
     */
    String[] includes() default {};
}
