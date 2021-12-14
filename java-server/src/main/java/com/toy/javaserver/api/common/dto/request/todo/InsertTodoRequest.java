package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class InsertTodoRequest {
    @CustomProperty
    @ApiParam(value = "유저 PK", readOnly = true)
    private Long userId;
    @CustomProperty
    @ApiParam(value = "유형", readOnly = true)
    private TodoType todoType;
    @CustomProperty
    @ApiParam(value = "제목", readOnly = true)
    private String title;
    @CustomProperty
    @ApiParam(value = "내용", readOnly = true)
    private String content;
    @CustomProperty
    @ApiParam(value = "작성자", readOnly = true)
    private String author;

    public void validate() {
        Optional.ofNullable(this.userId).orElseThrow(() -> new ApiException("유저 아이디 값이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.todoType).orElseThrow(() -> new ApiException("게시글 유형이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.title).orElseThrow(() -> new ApiException("제목이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.content).orElseThrow(() -> new ApiException("내용이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.author).orElseThrow(() -> new ApiException("작성자명이 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
