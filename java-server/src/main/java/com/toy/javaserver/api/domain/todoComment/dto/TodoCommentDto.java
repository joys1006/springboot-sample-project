package com.toy.javaserver.api.domain.todoComment.dto;

import com.toy.javaserver.api.common.utils.MapperSupport;
import com.toy.javaserver.api.domain.todoComment.enums.VisibilityType;
import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoCommentDto {

    private Long id;

    private Long userId;

    private Long todoId;

    private VisibilityType visibilityType;

    private String content;

    private String author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public TodoCommentDto(TodoCommentOrm todoCommentOrm) {
        MapperSupport.map(todoCommentOrm, this);
    }
}
