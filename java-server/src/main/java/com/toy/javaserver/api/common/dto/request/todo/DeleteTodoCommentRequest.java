package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class DeleteTodoCommentRequest {
    @CustomProperty
    @ApiParam(value = "유저 PK", readOnly = true)
    private Long userId;
}
