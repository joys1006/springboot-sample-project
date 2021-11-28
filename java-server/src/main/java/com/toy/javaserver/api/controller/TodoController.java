package com.toy.javaserver.api.controller;

import com.toy.javaserver.api.domain.todo.dto.TodoDto;
import com.toy.javaserver.api.domain.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/v1/todos")
@RestController("TodoController")
@Api(value = "할일 API 컨트롤러", description = "할일 API 컨트롤러")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/{todoId}")
    @ApiOperation(value = "할일 단건 조회")
    public TodoDto getTodos(
            @PathVariable(value = "todoId") Long todoId
    ) {
        return todoService.getTodo(todoId);
    }
}
