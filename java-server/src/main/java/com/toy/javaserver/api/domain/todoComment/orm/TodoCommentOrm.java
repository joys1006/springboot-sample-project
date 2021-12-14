package com.toy.javaserver.api.domain.todoComment.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.javaserver.api.common.dto.request.todo.InsertTodoCommentRequest;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todoComment.entity.TodoCommentEntity;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TodoComment")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoCommentOrm extends TodoCommentEntity implements Serializable {

    /**
     * 할일 정보
     * todo.id > todoComment.todo_id = 1 : N
     */
    @JsonBackReference
    @ManyToOne(targetEntity = TodoOrm.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TodoOrm todoOrm;

    public TodoCommentOrm createdTodoCommentOrm(Long todoId, Long userId, InsertTodoCommentRequest request) {
        this.setTodoId(todoId);
        this.setUserId(userId);
        this.setContent(request.getContent());
        this.setAuthor(request.getAuthor());
        this.setVisibilityType(request.getVisibilityType());

        return this;
    }
}
