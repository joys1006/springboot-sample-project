package com.toy.javaserver.api.domain.todo.service;

import com.toy.javaserver.api.domain.todo.dto.TodoDto;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todo.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoService Mock 테스트")
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Test
    @DisplayName("할일 단건 조회 서비스 성공 케이스")
    void getTodo() {
        // given
        TodoOrm todoOrm = new TodoOrm();

        todoOrm.setId(1L);
        todoOrm.setUserId(1L);
        todoOrm.setTodoType(TodoType.DO_TO);
        todoOrm.setTitle("테스트");
        todoOrm.setContent("테스트");
        todoOrm.setAuthor("관리자");
        todoOrm.setTodoCommentOrms(Collections.emptyList());
        todoOrm.setCreatedAt(LocalDateTime.of(2021, 11, 27, 00, 45, 37));
        todoOrm.setUpdatedAt(LocalDateTime.of(2021, 11, 27, 00, 45, 37));

        // when
        when(todoRepository.getById(1L)).thenReturn(todoOrm);

        // then
        TodoDto result = todoService.getTodo(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(todoOrm.getId());
        assertThat(result.getAuthor()).isEqualTo(todoOrm.getAuthor());
    }
}
