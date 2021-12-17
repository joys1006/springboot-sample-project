package com.toy.javaserver.api.domain.todoComment.service;

import com.toy.javaserver.api.common.dto.request.todo.InsertTodoCommentRequest;
import com.toy.javaserver.api.common.dto.request.todo.UpdateTodoCommentRequest;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todo.repository.TodoRepository;
import com.toy.javaserver.api.domain.todoComment.dto.TodoCommentDto;
import com.toy.javaserver.api.domain.todoComment.enums.VisibilityType;
import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import com.toy.javaserver.api.domain.todoComment.repository.TodoCommentRepository;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import com.toy.javaserver.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoCommentService Mock 테스트")
class TodoCommentServiceTest {

    @InjectMocks
    private TodoCommentService todoCommentService;

    @Mock
    private TodoCommentRepository todoCommentRepository;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("할일 댓글 등록 성공 테스트")
    void insertedTodoComment() {
        InsertTodoCommentRequest request = new InsertTodoCommentRequest();
        Long todoId = 1L;

        request.setUserId(2L);
        request.setAuthor("테스트");
        request.setContent("테스트 작성자");
        request.setVisibilityType(VisibilityType.PUBLIC);

        TodoOrm todoOrm = new TodoOrm();
        UserOrm userOrm = new UserOrm();

        todoOrm.setId(todoId);
        todoOrm.setUserId(request.getUserId());
        userOrm.setId(request.getUserId());

        TodoCommentOrm todoCommentOrm = new TodoCommentOrm()
                .createdTodoCommentOrm(todoId, userOrm.getId(), request);

        when(todoRepository.findById(todoId)).thenReturn(Optional.ofNullable(todoOrm));
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.ofNullable(userOrm));
        when(todoCommentRepository.save(any())).thenReturn(todoCommentOrm);

        TodoCommentDto result = todoCommentService.insertedTodoComment(todoId, request);

        assertThat(result).isNotNull();
        assertThat(result.getTodoId()).isEqualTo(todoCommentOrm.getTodoId());
        assertThat(result.getUserId()).isEqualTo(todoCommentOrm.getUserId());
        assertThat(result.getContent()).isEqualTo(todoCommentOrm.getContent());
        assertThat(result.getAuthor()).isEqualTo(todoCommentOrm.getAuthor());
    }

    @Test
    @DisplayName("할일 댓글 수정 성공 테스트")
    void updatedTodoComment() {
        UpdateTodoCommentRequest request = new UpdateTodoCommentRequest();
        Long todoId = 1L;
        Long todoCommentId = 1L;

        request.setUserId(2L);
        request.setContent("테스트 작성자");
        request.setVisibilityType(VisibilityType.PUBLIC);

        UserOrm userOrm = new UserOrm();

        userOrm.setId(request.getUserId());

        TodoCommentOrm todoCommentOrm = new TodoCommentOrm();

        todoCommentOrm.setTodoId(todoId);
        todoCommentOrm.setUserId(userOrm.getId());
        todoCommentOrm.setContent("원본 내용");

        when(todoCommentRepository.findByIdAndTodoId(todoCommentId, todoId)).thenReturn(Optional.ofNullable(todoCommentOrm));
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.ofNullable(userOrm));

        todoCommentOrm.setContent(request.getContent());

        when(todoCommentRepository.save(any())).thenReturn(todoCommentOrm);

        TodoCommentDto result = todoCommentService.updatedTodoComment(todoId, todoCommentId, request);

        assertThat(result).isNotNull();
        assertThat(result.getTodoId()).isEqualTo(todoCommentOrm.getTodoId());
        assertThat(result.getContent()).isEqualTo(todoCommentOrm.getContent()); // 내용이 리퀘스트에 요청한 내용대로 변경되면 OK
    }
}
