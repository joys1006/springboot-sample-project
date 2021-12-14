package com.toy.javaserver.api.domain.todoComment.repository;

import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCommentRepository extends JpaRepository<TodoCommentOrm, Long>, TodoCommentRepositoryCustom {
}
