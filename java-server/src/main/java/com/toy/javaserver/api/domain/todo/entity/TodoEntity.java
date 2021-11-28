package com.toy.javaserver.api.domain.todo.entity;

import com.toy.javaserver.api.common.type.CustomEnumType;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

//import com.toy.javaserver.api.domain.todo.enums.TodoType;

@MappedSuperclass
@Table(name = "Todo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Type(type = CustomEnumType.NAME)
    @Column(name = "todo_type")
    private TodoType todoType;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
