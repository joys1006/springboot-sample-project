package com.toy.javaserver.api.common.dto.request;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UnregisterRequestDto {
    @ApiParam(value = "회원 ID : 아이디", required = true)
    @CustomProperty(nullable = false)
    private String userId;
    @ApiParam(value = "비밀번호", required = true)
    @CustomProperty(nullable = false)
    private String password;
}
