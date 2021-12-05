package com.toy.javaserver.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.javaserver.api.common.helpers.AuthorizedControllerHelper;
import com.toy.javaserver.api.domain.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
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
}
