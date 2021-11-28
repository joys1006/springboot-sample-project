package com.toy.javaserver.api.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toy.javaserver.api.common.utils.MapperSupport;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;

    private String userId;

    private String password;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserDto(UserOrm userOrm) {
        MapperSupport.map(userOrm, this);
    }
}
