package com.toy.javaserver.api.domain.todoComment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoCommentRepositoryImpl implements TodoCommentRepositoryCustom {
    private final JPAQueryFactory factory;
}
