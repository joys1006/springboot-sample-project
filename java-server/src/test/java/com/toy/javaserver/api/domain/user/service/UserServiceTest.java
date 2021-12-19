package com.toy.javaserver.api.domain.user.service;

import com.toy.javaserver.api.common.dto.request.user.RegisterRequestDto;
import com.toy.javaserver.api.common.dto.request.user.SignInRequestDto;
import com.toy.javaserver.api.common.dto.request.user.UnregisterRequestDto;
import com.toy.javaserver.api.common.dto.response.user.SignInResponse;
import com.toy.javaserver.api.common.support.BCryptPasswordEncoderSupport;
import com.toy.javaserver.api.common.utils.sercurity.JwtTokenProvider;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import com.toy.javaserver.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Mock 테스트")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Spy
    private BCryptPasswordEncoderSupport passwordEncoder;

    private static final String testToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYzODEwODMyMywiZXhwIjoxNjM4MTExOTIzfQ.GGoHv6A0acQ9CZl30PMHiBDo7OjKENkHyiKYoPv1fFA";

    @Test
    @DisplayName("로그인 서비스 테스트")
    void signIn() {
        SignInRequestDto request = new SignInRequestDto();

        request.setUserId("test1");
        request.setPassword("1234");

        UserOrm userOrm = new UserOrm();

        userOrm.setUserId(request.getUserId());
        userOrm.setPassword("$2y$10$lvrwYg8h7LSMSDo/i8HrwOuR3gD/lC4oYYF7YevobQ2xw7xJ8WHsm");

        when(userRepository.findByUserId(request.getUserId())).thenReturn(Optional.of(userOrm));
        when(jwtTokenProvider.createToken(anyString())).thenReturn(this.testToken);

        SignInResponse result = userService.signIn(request);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo(this.testToken);
    }

    @Test
    @DisplayName("회원가입 서비스 테스트")
    void register() {
        RegisterRequestDto request = new RegisterRequestDto();

        request.setUserId("test");
        request.setPassword("1234");
        request.setName("테스트");

        UserOrm userOrm = new UserOrm();

        userOrm.setId(3L);
        userOrm.setUserId(request.getUserId());
        userOrm.setPassword(request.getPassword());
        userOrm.setName(request.getName());
        userOrm.setCreatedAt(LocalDateTime.now());
        userOrm.setUpdatedAt(LocalDateTime.now());

        when(userRepository.save(any())).thenReturn(userOrm);
        when(jwtTokenProvider.createToken(anyString())).thenReturn(this.testToken);

        SignInResponse result = userService.register(request);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNotNull();
    }

    @Test
    @DisplayName("회원탈퇴 서비스 테스트")
    void unregister() {
        UnregisterRequestDto request = new UnregisterRequestDto();

        request.setUserId("test");
        request.setPassword("1234");

        UserOrm userOrm = new UserOrm();

        userOrm.setUserId(request.getUserId());
        userOrm.setPassword("$2y$10$lvrwYg8h7LSMSDo/i8HrwOuR3gD/lC4oYYF7YevobQ2xw7xJ8WHsm");

        when(userRepository.findByUserId(request.getUserId())).thenReturn(Optional.of(userOrm));

        HttpStatus result = userService.unregister(request);

        assertThat(result).isNotNull();
        assertThat(result.getReasonPhrase()).isEqualTo("OK");
    }
}
