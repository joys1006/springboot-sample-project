package com.toy.javaserver.api.controller;

import com.toy.javaserver.api.common.dto.request.RegisterRequestDto;
import com.toy.javaserver.api.common.dto.request.SignInRequestDto;
import com.toy.javaserver.api.common.dto.request.UnregisterRequestDto;
import com.toy.javaserver.api.common.dto.response.RegisterResponse;
import com.toy.javaserver.api.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/v1/users")
@RestController("UserController")
@Api(value = "유저 인증 컨트롤러", description = "유저 인증 컨트롤러")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @PostMapping(value = "/sign-in")
    public RegisterResponse signIn(@RequestBody @Valid SignInRequestDto request) {
        request.validate();

        return userService.signIn(request);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping(value = "/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequestDto request) {
        request.validate();

        return userService.register(request);
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴 API")
    @PostMapping(value = "/unregister")
    public HttpStatus unregister(@RequestBody @Valid UnregisterRequestDto request) {
        request.validate();

        return userService.unregister(request);
    }
}
