package com.toy.javaserver.api.domain.todo.enums;

import com.toy.javaserver.api.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoType implements BaseEnum {
    DO_TO(1, "할일", ""),
    PROGRESS(2, "진행중", ""),
    STOP(3, "중지", ""),
    COMPLETE(4, "완료", "");

    private final Integer code;
    private final String name;
    private final String desc;
}
