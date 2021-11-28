package com.toy.javaserver.api.domain.user.orm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toy.javaserver.api.domain.user.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserOrm extends UserEntity implements Serializable {
}
