package com.toy.javaserver.api.common.dto.request;

import com.toy.javaserver.api.common.exception.ApiException;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class RegisterRequestDto {
    @ApiParam(value = "회원ID", required = true)
    public String userId;
    @ApiParam(value = "비밀번호", required = true)
    public String password;
    @ApiParam(value = "이름", required = true)
    public String name;

    public void validate() {
        Optional.ofNullable(this.userId).orElseThrow(() -> new ApiException("유저 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.password).orElseThrow(() -> new ApiException("비밀번호가 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.name).orElseThrow(() -> new ApiException("회원 이름이 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
