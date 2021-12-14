package com.toy.javaserver.api.controller;

import com.toy.javaserver.api.common.dto.request.todo.InsertTodoCommentRequest;
import com.toy.javaserver.api.common.dto.request.todo.InsertTodoRequest;
import com.toy.javaserver.api.common.dto.request.todo.UpdateTodoRequest;
import com.toy.javaserver.api.domain.todo.dto.TodoDto;
import com.toy.javaserver.api.domain.todo.service.TodoService;
import com.toy.javaserver.api.domain.todoComment.dto.TodoCommentDto;
import com.toy.javaserver.api.domain.todoComment.service.TodoCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/v1/todos")
@RestController("TodoController")
@Api(value = "할일 API 컨트롤러", description = "할일 API 컨트롤러")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private final TodoCommentService todoCommentService;

    @GetMapping("/{todoId}")
    @ApiOperation(value = "할일 단건 조회")
    public TodoDto getTodo(
            @PathVariable(value = "todoId") Long todoId
    ) {
        return todoService.getTodo(todoId);
    }

    @PostMapping
    @ApiOperation(value = "할일 등록")
    public TodoDto insertedTodo(
            @RequestBody @Valid InsertTodoRequest request
    ) {
        request.validate();

        return todoService.insertedTodo(request);
    }

    @PutMapping("/{todoId}")
    @ApiOperation(value = "할일 수정")
    public TodoDto updatedTodo(
            @PathVariable(value = "todoId") Long todoId,
            @RequestBody @Valid UpdateTodoRequest request
    ) {
        return todoService.updatedTodo(todoId, request);
    }

    @DeleteMapping("/{todoId}")
    @ApiOperation(value = "할일 삭제")
    public HttpStatus deletedTodo(
            @PathVariable(value = "todoId") Long todoId
    ) {
        return todoService.deletedTodo(todoId);
    }

    @PostMapping("/{todoId}/comment")
    public TodoCommentDto insertedTodoComment(
            @PathVariable(value = "todoId") Long todoId,
            @RequestBody @Valid InsertTodoCommentRequest request
    ) {
        return todoCommentService.insertedTodoComment(todoId, request);
    }
}
