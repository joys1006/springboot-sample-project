package com.toy.javaserver.api.domain.user.orm;

import com.toy.javaserver.api.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserOrm extends UserEntity implements Serializable {
}
