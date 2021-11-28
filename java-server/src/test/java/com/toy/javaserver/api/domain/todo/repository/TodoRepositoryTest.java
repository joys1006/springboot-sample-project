package com.toy.javaserver.api.domain.todo.repository;

import com.toy.javaserver.api.configuration.TestDataManagerConfiguration;
import com.toy.javaserver.api.configuration.TestDataSourceConfiguration;
import com.toy.javaserver.api.configuration.TestQueryDslConfiguration;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import com.toy.javaserver.api.domain.todo.orm.TodoOrm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"local"})
@DataJpaTest
@Import(value = {TestQueryDslConfiguration.class, TestDataManagerConfiguration.class, TestDataSourceConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private TodoOrm todoOrm;

    @BeforeEach
    void setUp() {
        // given
        TodoOrm todoOrm = new TodoOrm();

        todoOrm.setId(1L);
        todoOrm.setUserId(3L);
        todoOrm.setTodoType(TodoType.DO_TO);
        todoOrm.setTitle("테스트");
        todoOrm.setContent("테스트");
        todoOrm.setAuthor("관리자");
        todoOrm.setTodoCommentOrms(Collections.emptyList());
        todoOrm.setCreatedAt(LocalDateTime.of(2021, 11, 27, 00, 45, 37));
        todoOrm.setUpdatedAt(LocalDateTime.of(2021, 11, 27, 00, 45, 37));

        this.todoOrm = todoOrm;
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("할일 단건 조회 JPA 테스트")
    void getTodo() {
        // then
        TodoOrm result = todoRepository.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(todoOrm.getId());
        assertThat(result.getAuthor()).isEqualTo(todoOrm.getAuthor());
    }
}
