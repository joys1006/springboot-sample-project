package com.toy.javaserver.api.common.utils.reflect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class ReflectUtil {
    /**
     * null 인 필드 목록 가져오기
     */
    public static List<String> getNullFieldNames(Object o) {
        List<String> fields = new ArrayList<>();

        ReflectionUtils.doWithFields(o.getClass(), field -> {
            field.setAccessible(true);
            Object v = field.get(o);

            if (v == null) fields.add(field.getName());
        });

        return fields;
    }

    /**
     * null이 아닌 필드 목록 가져오기
     */
    public static List<String> getNonNullFieldNames(Object o) {
        List<String> fields = new ArrayList<>();

        ReflectionUtils.doWithFields(o.getClass(), field -> {
            field.setAccessible(true);
            Object v = field.get(o);

            if (v != null) fields.add(field.getName());
        });

        return fields;
    }

    /**
     * Null 인 필드 존재 체크
     */
    public static boolean hasNullFields(Object o) {
        List<String> nullFieldList = getNullFieldNames(o);

        return nullFieldList.size() > 0;
    }

    /**
     * 모든 필드가 Null 인지 체크
     */
    public static boolean isAllFieldsNull(Object o) {
        AtomicBoolean isNull = new AtomicBoolean(true);

        ReflectionUtils.doWithFields(o.getClass(), field -> {
            field.setAccessible(true);
            Object v = field.get(o);

            if (v != null) isNull.set(false);
        });

        return isNull.get();
    }
}
