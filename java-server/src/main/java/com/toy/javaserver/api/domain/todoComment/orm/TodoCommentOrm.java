package com.toy.javaserver.api.domain.todoComment.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import com.toy.javaserver.api.domain.todoComment.entity.TodoCommentEntity;
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
}
