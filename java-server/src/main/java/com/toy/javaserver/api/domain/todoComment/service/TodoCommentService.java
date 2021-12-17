package com.toy.javaserver.api.domain.todoComment.service;

import com.toy.javaserver.api.common.dto.request.todo.DeleteTodoCommentRequest;
import com.toy.javaserver.api.common.dto.request.todo.InsertTodoCommentRequest;
import com.toy.javaserver.api.common.dto.request.todo.UpdateTodoCommentRequest;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todo.repository.TodoRepository;
import com.toy.javaserver.api.domain.todoComment.dto.TodoCommentDto;
import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import com.toy.javaserver.api.domain.todoComment.repository.TodoCommentRepository;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import com.toy.javaserver.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoCommentService {

    private final TodoCommentRepository todoCommentRepository;

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    @Transactional
    public TodoCommentDto insertedTodoComment(Long todoId, InsertTodoCommentRequest request) {
        TodoOrm todoOrm = todoRepository.findById(todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        UserOrm userOrm = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        if (!userOrm.getId().equals(todoOrm.getUserId())) {
            throw new ApiException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

        TodoCommentOrm todoCommentOrm = new TodoCommentOrm()
                .createdTodoCommentOrm(todoOrm.getId(), userOrm.getId(), request);

        TodoCommentOrm insertedTodoCommentOrm = todoCommentRepository.save(todoCommentOrm);

        return new TodoCommentDto(insertedTodoCommentOrm);
    }

    @Transactional
    public TodoCommentDto updatedTodoComment(
            Long todoId,
            Long todoCommentId,
            UpdateTodoCommentRequest request
    ) {
        TodoCommentOrm todoCommentOrm = todoCommentRepository
                .findByIdAndTodoId(todoCommentId, todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        UserOrm userOrm = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        if (!userOrm.getId().equals(todoCommentOrm.getUserId())) {
            throw new ApiException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

        todoCommentOrm.setContent(request.getContent());
        todoCommentOrm.setVisibilityType(request.getVisibilityType());

        TodoCommentOrm updatedTodoCommentOrm = todoCommentRepository.save(todoCommentOrm);

        return new TodoCommentDto(updatedTodoCommentOrm);
    }

    @Transactional
    public HttpStatus deletedTodoComment(
            Long todoId,
            Long todoCommentId,
            DeleteTodoCommentRequest request
    ) {
        TodoCommentOrm todoCommentOrm = todoCommentRepository
                .findByIdAndTodoId(todoCommentId, todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        UserOrm userOrm = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT));

        if (!userOrm.getId().equals(todoCommentOrm.getUserId())) {
            throw new ApiException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

        todoCommentRepository.deleteById(todoCommentOrm.getId());

        return HttpStatus.OK;
    }
}
