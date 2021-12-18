package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.support.validator.ValidateSupport;
import com.toy.javaserver.api.domain.todoComment.enums.VisibilityType;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateTodoCommentRequest extends ValidateSupport {
    @CustomProperty
    @ApiParam(value = "유저 PK", readOnly = true)
    private Long userId;
    @CustomProperty
    @ApiParam(value = "공개여부", readOnly = true)
    private VisibilityType visibilityType = VisibilityType.PUBLIC;
    @CustomProperty
    @ApiParam(value = "내용", readOnly = true)
    @Size(min = 1, max = 100, message = "내용 입력 길이는 1~100까지만 입력가능합니다.")
    private String content;

    public void validate() {
        // 전체 값에 대한 null 체크
        this.modelFieldsValuesNullException(this);
    }
}
