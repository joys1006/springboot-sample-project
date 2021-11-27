package com.toy.javaserver.api.common.exception;

import com.toy.javaserver.api.common.enums.BaseEnum;

/**
 * BaseEnum에 검증 로직에 대한 예외처리
 * @see BaseEnum
 */
public class UnknownEnumCodeException extends RuntimeException {
    public UnknownEnumCodeException(Class cls, Object code) {
        super("UnknownEnumCodeException : Enum code value -> [" + code + "]{" + code.getClass().getName() + ") not in " + cls.getName());
    }
}
