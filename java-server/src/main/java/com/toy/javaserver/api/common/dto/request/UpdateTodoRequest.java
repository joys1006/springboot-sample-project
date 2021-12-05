package com.toy.javaserver.api.common.dto.request;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UpdateTodoRequest {
    @CustomProperty
    @ApiParam(value = "유형", readOnly = true)
    private TodoType todoType;
    @CustomProperty
    @ApiParam(value = "제목", readOnly = true)
    private String title;
    @CustomProperty
    @ApiParam(value = "내용", readOnly = true)
    private String content;
}
