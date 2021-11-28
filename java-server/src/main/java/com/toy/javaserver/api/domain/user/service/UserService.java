package com.toy.javaserver.api.domain.user.service;

import com.toy.javaserver.api.common.dto.request.SignInRequestDto;
import com.toy.javaserver.api.common.dto.request.SignUpRequestDto;
import com.toy.javaserver.api.common.dto.response.SignInResponse;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.common.support.BCryptPasswordEncoderSupport;
import com.toy.javaserver.api.common.utils.sercurity.JwtTokenProvider;
import com.toy.javaserver.api.domain.user.dto.UserDto;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import com.toy.javaserver.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoderSupport passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequestDto request) {
        UserDto user = userRepository.findByUserId(request.getUserId()).map(UserDto::new)
                .orElseThrow(() -> new ApiException("로그인 정보가 없습니다.", HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException("패스워드가 틀립니다.", HttpStatus.BAD_REQUEST);
        }

        String token = jwtTokenProvider.createToken(String.valueOf(user.getUserId()));

        SignInResponse result = new SignInResponse();

        result.setToken(token);

        return result;
    }

    @Transactional
    public SignInResponse signUp(SignUpRequestDto request) {
        UserOrm userOrm = new UserOrm();

        userOrm.setUserId(request.getUserId());
        userOrm.setPassword(passwordEncoder.encode(request.getPassword()));
        userOrm.setName(request.getName());
        userOrm.setCreatedAt(LocalDateTime.now());

        boolean isUser = userRepository.findByUserId(request.getUserId()).isPresent();

        if (isUser) {
            throw new ApiException("동일한 아이디가 있습니다.", HttpStatus.BAD_REQUEST);
        }

        UserOrm saveUserOrm = userRepository.save(userOrm);

        String token = jwtTokenProvider.createToken(saveUserOrm.getUserId());

        SignInResponse result = new SignInResponse();

        result.setToken(token);

        return result;
    }
}
