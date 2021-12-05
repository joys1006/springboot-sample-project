package com.toy.javaserver.api.domain.todo.service;

import com.toy.javaserver.api.common.dto.request.InsertTodoRequest;
import com.toy.javaserver.api.common.dto.request.UpdateTodoRequest;
import com.toy.javaserver.api.common.utils.MapperSupport;
import com.toy.javaserver.api.domain.todo.dto.TodoDto;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public TodoDto getTodo(Long todoId) {
        TodoOrm todoOrm = todoRepository.getById(todoId);

        TodoDto result = new TodoDto(todoOrm);

        return result;
    }

    @Transactional
    public TodoDto insertedTodo(InsertTodoRequest request) {
        TodoOrm todoOrm = MapperSupport.map(request, TodoOrm.class);

        TodoOrm insertedTodoOrm = todoRepository.save(todoOrm);

        TodoDto result = new TodoDto(insertedTodoOrm);

        return result;
    }

    @Transactional
    public TodoDto updatedTodo(Long todoId, UpdateTodoRequest request) {
        TodoOrm todoOrm = todoRepository.getById(todoId)
                .setUpdateTodoOrm(request);

        TodoOrm updatedTodoOrm = todoRepository.save(todoOrm);

        TodoDto result = new TodoDto(updatedTodoOrm);

        return result;
    }

    @Transactional
    public HttpStatus deletedTodo(Long todoId) {

        todoRepository.deleteById(todoId);

        return HttpStatus.OK;
    }
}
