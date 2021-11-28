package com.toy.javaserver.api.controller;

import com.toy.javaserver.api.common.dto.request.SignInRequestDto;
import com.toy.javaserver.api.common.dto.request.SignUpRequestDto;
import com.toy.javaserver.api.common.dto.response.SignInResponse;
import com.toy.javaserver.api.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public SignInResponse signIn(@RequestBody @Valid SignInRequestDto request) {
        return userService.signIn(request);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping(value = "/sign-up")
    public SignInResponse singUp(@RequestBody @Valid SignUpRequestDto request) {
        return userService.signUp(request);
    }
}
