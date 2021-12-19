package com.toy.javaserver.api.common.dto.response.user;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import lombok.Data;

@Data
public class SignInResponse {
    @CustomProperty(value = "토큰 값")
    private String token;
}
