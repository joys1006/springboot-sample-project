package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.support.validator.ValidateSupport;
import com.toy.javaserver.api.domain.todo.enums.TodoType;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateTodoRequest extends ValidateSupport {
    @CustomProperty
    @ApiParam(value = "유형")
    private TodoType todoType;
    @CustomProperty
    @ApiParam(value = "제목")
    @Size(min = 1, max = 50, message = "제목 입력 길이는 1~50까지만 입력가능합니다.")
    private String title;
    @CustomProperty
    @ApiParam(value = "내용")
    @Size(min = 1, max = 1500, message = "내용 입력 길이는 1~1500까지만 입력가능합니다.")
    private String content;

    public void validate() {
        // 전체 값에 대한 null 체크
        this.modelFieldsValuesNullException(this);
    }
}
