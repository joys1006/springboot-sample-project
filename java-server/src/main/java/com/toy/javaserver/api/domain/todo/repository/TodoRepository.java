package com.toy.javaserver.api.domain.todo.repository;

import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TodoRepository extends JpaRepository<TodoOrm, Long>, TodoRepositoryCustom {
}
