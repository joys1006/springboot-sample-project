package com.toy.javaserver.api.common.dto.request.todo;

import com.toy.javaserver.api.common.annotation.CustomProperty;
import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.domain.todoComment.enums.VisibilityType;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
import java.util.Optional;

@Data
public class UpdateTodoCommentRequest {
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
        Optional.ofNullable(this.userId).orElseThrow(() -> new ApiException("유저 아이디 값이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.visibilityType).orElseThrow(() -> new ApiException("공개여부 선택 값이 없습니다.", HttpStatus.BAD_REQUEST));
        Optional.ofNullable(this.content).orElseThrow(() -> new ApiException("내용이 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
