package com.toy.javaserver.api.controller;

import com.toy.javaserver.api.common.dto.request.todo.*;
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
        return todoService.insertedTodo(request);
    }

    @PutMapping("/{todoId}")
    @ApiOperation(value = "할일 수정")
    public TodoDto updatedTodo(
            @PathVariable(value = "todoId") Long todoId,
            @RequestBody @Valid UpdateTodoRequest request
    ) {
        request.validate();

        return todoService.updatedTodo(todoId, request);
    }

    @DeleteMapping("/{todoId}")
    @ApiOperation(value = "할일 삭제")
    public HttpStatus deletedTodo(
            @PathVariable(value = "todoId") Long todoId
    ) {
        return todoService.deletedTodo(todoId);
    }

    @PostMapping("/{todoId}/comments")
    @ApiOperation(value ="할일 댓글 등록")
    public TodoCommentDto insertedTodoComment(
            @PathVariable(value = "todoId") Long todoId,
            @RequestBody @Valid InsertTodoCommentRequest request
    ) {
        return todoCommentService.insertedTodoComment(todoId, request);
    }

    @PatchMapping("/{todoId}/comments/{todoCommentId}")
    @ApiOperation(value = "할일 댓글 수정")
    public TodoCommentDto updatedTodoComment(
            @PathVariable(value = "todoId") Long todoId,
            @PathVariable(value = "todoCommentId") Long todoCommentId,
            @RequestBody @Valid UpdateTodoCommentRequest request
    ) {
        request.validate();

        return todoCommentService.updatedTodoComment(todoId, todoCommentId, request);
    }

    @DeleteMapping("/{todoId}/comments/{todoCommentId}")
    @ApiOperation(value = "할일 댓글 삭제")
    public HttpStatus deletedTodoComment(
            @PathVariable(value = "todoId") Long todoId,
            @PathVariable(value = "todoCommentId") Long todoCommentId,
            @RequestBody @Valid DeleteTodoCommentRequest request
    ) {
        return todoCommentService.deletedTodoComment(todoId, todoCommentId, request);
    }
}
