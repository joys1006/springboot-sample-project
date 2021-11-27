package com.toy.javaserver.api.domain.todo.dto;

import com.toy.javaserver.api.common.utils.MapperSupport;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todoComment.dto.TodoCommentDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TodoDto {
    private Long id;

    private Long userId;

    private TodoType todoType;

    private String title;

    private String content;

    private List<TodoCommentDto> todoComments = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public TodoDto(TodoOrm todoOrm) {
        MapperSupport.map(todoOrm, this);

        // 댓글 리스트 매핑
        this.todoComments = MapperSupport.mapAll(todoOrm.getTodoCommentOrms(), TodoCommentDto.class);
    }
}
