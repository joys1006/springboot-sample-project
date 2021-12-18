package com.toy.javaserver.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.javaserver.api.common.dto.request.RegisterRequestDto;
import com.toy.javaserver.api.common.dto.request.SignInRequestDto;
import com.toy.javaserver.api.common.dto.request.UnregisterRequestDto;
import com.toy.javaserver.api.common.helpers.AuthorizedControllerHelper;
import com.toy.javaserver.api.domain.user.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { UserController.class })
@DisplayName("UserControllerTest 테스트")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() throws Exception {
        AuthorizedControllerHelper authorizedControllerHelper = new AuthorizedControllerHelper();

        mockMvc = authorizedControllerHelper.getSecurityAppliedMockMvc(context);

        httpHeaders = new HttpHeaders();

        httpHeaders.set("Accept", "application/json");
    }

    @Test
    @DisplayName("로그인 응답 테스트")
    void signIn() throws Exception {
        SignInRequestDto request = new SignInRequestDto();

        request.setUserId("test1");
        request.setPassword("1234");

        MockHttpServletRequestBuilder requestBuilder = post("/v1/users/sign-in")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 응답 테스트")
    void register() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto();

        request.setUserId("test");
        request.setPassword("1234");
        request.setName("테스트");

        MockHttpServletRequestBuilder requestBuilder = post("/v1/users/register")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원탈퇴 응답 테스트")
    void unregister() throws Exception {
        UnregisterRequestDto request = new UnregisterRequestDto();

        request.setUserId("test");
        request.setPassword("1234");

        MockHttpServletRequestBuilder requestBuilder = post("/v1/users/unregister")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
