package com.toy.javaserver.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.javaserver.api.common.dto.request.todo.InsertTodoCommentRequest;
import com.toy.javaserver.api.common.dto.request.todo.UpdateTodoCommentRequest;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.common.helpers.AuthorizedControllerHelper;
import com.toy.javaserver.api.domain.todo.service.TodoService;
import com.toy.javaserver.api.domain.todoComment.enums.VisibilityType;
import com.toy.javaserver.api.domain.todoComment.service.TodoCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { TodoController.class })
@DisplayName("TodoController 테스트")
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TodoCommentService todoCommentService;

    @MockBean
    private TodoController todoController;

    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() throws Exception {
        AuthorizedControllerHelper authorizedControllerHelper = new AuthorizedControllerHelper();

        mockMvc = authorizedControllerHelper.getSecurityAppliedMockMvc(context);

        httpHeaders = new HttpHeaders();

        httpHeaders.set("Accept", "application/json");
    }

    @Test
    @DisplayName("할일 단건 조회 성공 테스트")
    void getTodo() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/v1/todos/{todoId}", 1)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("할일 댓글 등록 성공 테스트")
    void insertedTodoComment() throws Exception {
        InsertTodoCommentRequest request = new InsertTodoCommentRequest();

        request.setUserId(2L);
        request.setAuthor("테스트");
        request.setContent("테스트 작성자");
        request.setVisibilityType(VisibilityType.PUBLIC);

        MockHttpServletRequestBuilder requestBuilder = post("/v1/todos/{todoId}/comments", 1)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("할일 댓글 수정 성공 테스트")
    void updatedTodoComment() throws Exception {
        UpdateTodoCommentRequest request = new UpdateTodoCommentRequest();

        request.setUserId(2L);
        request.setContent("테스트 작성자");
        request.setVisibilityType(VisibilityType.PUBLIC);

        MockHttpServletRequestBuilder requestBuilder = patch("/v1/todos/1/comments/1")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }
}
