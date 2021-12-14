package com.toy.javaserver.api.domain.user.repository;

import com.toy.javaserver.api.domain.user.orm.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserOrm, Long>, UserRepositoryCustom {
    Optional<UserOrm> findByUserId(String userId);
}
