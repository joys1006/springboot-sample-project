package com.toy.javaserver.api.domain.todoComment.enums;

import com.toy.javaserver.api.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisibilityType implements BaseEnum {
    PUBLIC(1, "공개", "노출"),
    PRIVATE(2, "비공개", "미노출");

    private Integer code;
    private String name;
    private String desc;
}
