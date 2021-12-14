package com.toy.javaserver.api.domain.todo.orm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toy.javaserver.api.common.dto.request.todo.UpdateTodoRequest;
import com.toy.javaserver.api.domain.todo.entity.TodoEntity;
import com.toy.javaserver.api.domain.todoComment.orm.TodoCommentOrm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Todo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoOrm extends TodoEntity implements Serializable {

    /**
     * todoComment 정보
     * todo.id > todoComment.id = 1 : N
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "todoOrm", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @OrderBy("id DESC")
    private List<TodoCommentOrm> todoCommentOrms = new ArrayList<>();

    public TodoOrm setUpdateTodoOrm(UpdateTodoRequest request) {
        Optional.ofNullable(request.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(request.getTodoType()).ifPresent(this::setTodoType);
        Optional.ofNullable(request.getTitle()).ifPresent(this::setTitle);

        return this;
    }
}
