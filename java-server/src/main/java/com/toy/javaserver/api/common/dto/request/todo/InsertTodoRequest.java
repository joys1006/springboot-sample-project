package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
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
    @Size(min = 1, max = 50, message = "제목 입력 길이는 1~50까지만 입력가능합니다.")
    private String title;
    @CustomProperty
    @ApiParam(value = "내용", readOnly = true)
    @Size(min = 1, max = 1500, message = "내용 입력 길이는 1~1500까지만 입력가능합니다.")
    private String content;
    @CustomProperty
    @ApiParam(value = "작성자", readOnly = true)
    @Size(min = 1, max = 20, message = "작성자 입력 길이는 1~20까지만 입력가능합니다.")
    private String author;

    public void validate() {
        Optional.ofNullable(this.userId).orElseThrow(() -> new ApiException("유저 아이디 값이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.todoType).orElseThrow(() -> new ApiException("게시글 유형이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.title).orElseThrow(() -> new ApiException("제목이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.content).orElseThrow(() -> new ApiException("내용이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.author).orElseThrow(() -> new ApiException("작성자명이 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
