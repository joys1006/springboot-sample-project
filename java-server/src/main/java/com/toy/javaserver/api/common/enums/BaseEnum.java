package com.toy.javaserver.api.common.enums;

import com.toy.javaserver.api.common.exception.UnknownEnumCodeException;
import com.toy.javaserver.api.configuration.JacksonConfiguration;

/**
 * @description 커스텀 Enum 인터페이스
 * @comment Entity Enum 공통 컨버팅 적용을 위한 인터페이스
 * @see JacksonConfiguration
 */
public interface BaseEnum {
    Object getCode();
    String getName();
    String getDesc();

    /**
     * 엔티티에 세팅된 코드 검증
     */
    static <S extends BaseEnum> S getEnum(Class<S> cls, Object code) {
        for (BaseEnum e : cls.getEnumConstants()) {
            if (code.equals(e.getCode()) || code.equals(e.getName()) || code.equals(e.getDesc())) return (S) e;
        }

        throw new UnknownEnumCodeException(cls, code);
    }

    static <S extends BaseEnum> S getStringToEnumConverterEquals(Class<S> cls, Object code) {
        for (BaseEnum e : cls.getEnumConstants()) {
            if (code.equals(e.getCode().toString()) || code.equals(e.getName()) || code.equals(e.getDesc())) return (S) e;
        }

        throw new UnknownEnumCodeException(cls, code);
    }
}
