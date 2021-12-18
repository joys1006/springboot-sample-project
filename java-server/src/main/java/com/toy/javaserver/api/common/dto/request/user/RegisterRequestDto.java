package com.toy.javaserver.api.common.dto.request.user;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @ApiParam(value = "회원ID", required = true)
    @CustomProperty(nullable = false)
    public String userId;
    @ApiParam(value = "비밀번호", required = true)
    @CustomProperty(nullable = false)
    public String password;
    @ApiParam(value = "이름", required = true)
    @CustomProperty(nullable = false)
    public String name;
}
