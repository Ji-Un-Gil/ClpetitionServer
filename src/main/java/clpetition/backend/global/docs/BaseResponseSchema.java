package clpetition.backend.global.docs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "기본 응답 포맷")
public interface BaseResponseSchema<T> {

    @Schema(description = "서버 응답 코드", example = "SUCCESS")
    String getCode();

    @Schema(description = "응답 메시지", example = "요청에 성공했습니다.")
    String getMessage();

    @Schema(description = "응답 데이터")
    T getResult();
}
