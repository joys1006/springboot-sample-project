package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class InsertTodoRequest {
    @CustomProperty(nullable = false)
    @ApiParam(value = "유저 PK", readOnly = true)
    private Long userId;
    @CustomProperty(nullable = false)
    @ApiParam(value = "유형", readOnly = true)
    private TodoType todoType;
    @CustomProperty(nullable = false)
    @ApiParam(value = "제목", readOnly = true)
    @Size(min = 1, max = 50, message = "제목 입력 길이는 1~50까지만 입력가능합니다.")
    private String title;
    @CustomProperty(nullable = false)
    @ApiParam(value = "내용", readOnly = true)
    @Size(min = 1, max = 1500, message = "내용 입력 길이는 1~1500까지만 입력가능합니다.")
    private String content;
    @CustomProperty(nullable = false)
    @ApiParam(value = "작성자", readOnly = true)
    @Size(min = 1, max = 20, message = "작성자 입력 길이는 1~20까지만 입력가능합니다.")
    private String author;
}
