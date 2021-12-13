package com.toy.javaserver.api.common.dto.request;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.exception.ApiException;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class UnregisterRequestDto {
    @ApiParam(value = "회원 ID : 아이디", required = true)
    @CustomProperty(nullable = false)
    private String userId;
    @ApiParam(value = "비밀번호", required = true)
    @CustomProperty(nullable = false)
    private String password;

    public void validate() {
        Optional.ofNullable(this.userId).orElseThrow(() -> new ApiException("유저 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.password).orElseThrow(() -> new ApiException("비밀번호가 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
