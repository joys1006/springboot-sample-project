package com.toy.javaserver.api.common.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @ApiParam(value = "회원ID", required = true)
    public String userId;
    @ApiParam(value = "비밀번호", required = true)
    public String password;
    @ApiParam(value = "이름", required = true)
    public String name;
}
